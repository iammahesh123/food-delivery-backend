package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.entity.OrderItemEntity;
import com.food.fooddeliverybackend.entity.OrdersEntity;
import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.enums.PaymentMethod;
import com.food.fooddeliverybackend.enums.PaymentStatus;
import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.exception.ResourceNotFoundException;
import com.food.fooddeliverybackend.mapper.OrderMapper;
import com.food.fooddeliverybackend.model.OrderItemRequestDTO;
import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.FoodItemRepository;
import com.food.fooddeliverybackend.repository.OrdersRepository;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.security.SecurityUtil;
import com.food.fooddeliverybackend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final OrderMapper orderMapper;
    private final ModelMapper modelMapper;
    private final SecurityUtil securityUtil;

    public OrderServiceImpl(OrdersRepository ordersRepository,
                            RestaurantRepository restaurantRepository,
                            FoodItemRepository foodItemRepository,
                            OrderMapper orderMapper,
                            ModelMapper modelMapper,
                            SecurityUtil securityUtil) {
        this.ordersRepository = ordersRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderMapper = orderMapper;
        this.modelMapper = modelMapper;
        this.securityUtil = securityUtil;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        UserEntity customer = securityUtil.getCurrentUser();

        RestaurantEntity restaurant = restaurantRepository.findById(orderRequestDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant not found with id: " + orderRequestDTO.getRestaurantId()));

        OrdersEntity order = new OrdersEntity();
        order.setName(orderRequestDTO.getName() != null ? orderRequestDTO.getName() : customer.getFullName());
        order.setUserEntity(customer);
        order.setRestaurantEntity(restaurant);
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setPaymentMethod(orderRequestDTO.getPaymentMethod() != null
                ? orderRequestDTO.getPaymentMethod() : PaymentMethod.CASH_ON_DELIVERY);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItemEntity> orderItems = new ArrayList<>();
        double total = 0;
        if (orderRequestDTO.getItems() != null && !orderRequestDTO.getItems().isEmpty()) {
            for (OrderItemRequestDTO itemRequest : orderRequestDTO.getItems()) {
                FoodItemEntity foodItem = foodItemRepository.findById(itemRequest.getFoodItemId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Food item not found with id: " + itemRequest.getFoodItemId()));
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setTitle(foodItem.getName());
                orderItem.setDescription(foodItem.getDescription());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(foodItem.getPrice() != null ? foodItem.getPrice() : 0);
                orderItem.setFoodItemEntity(foodItem);
                orderItem.setOrder(order);
                total += orderItem.getPrice() * orderItem.getQuantity();
                orderItems.add(orderItem);
            }
        }
        order.setOrderItemEntities(orderItems);
        order.setTotalAmount(!orderItems.isEmpty() ? total : orderRequestDTO.getTotalAmount());

        OrdersEntity savedOrder = ordersRepository.save(order);
        return orderMapper.orderResponseDTO(savedOrder, modelMapper);
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        OrdersEntity order = findOrder(id);
        if (orderRequestDTO.getName() != null) {
            order.setName(orderRequestDTO.getName());
        }
        if (orderRequestDTO.getDeliveryAddress() != null) {
            order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        }
        if (orderRequestDTO.getPaymentMethod() != null) {
            order.setPaymentMethod(orderRequestDTO.getPaymentMethod());
        }
        if (orderRequestDTO.getStatus() != null) {
            order.setStatus(orderRequestDTO.getStatus());
        }
        OrdersEntity savedOrder = ordersRepository.save(order);
        return orderMapper.orderResponseDTO(savedOrder, modelMapper);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        return orderMapper.orderResponseDTO(findOrder(id), modelMapper);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(PageModel pageModel) {
        Page<OrdersEntity> orders = ordersRepository.findAll(applyPagination(pageModel));
        return orders.stream().map(ordersEntity ->  orderMapper.orderResponseDTO(ordersEntity, modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        ordersRepository.delete(findOrder(id));
    }

    @Override
    public List<OrderResponseDTO> getMyOrders() {
        Long userId = securityUtil.getCurrentUser().getId();
        return toDTOs(ordersRepository.findByUserEntity_IdOrderByOrderDateDesc(userId));
    }

    @Override
    public OrderResponseDTO cancelMyOrder(Long id) {
        OrdersEntity order = findOrder(id);
        UserEntity currentUser = securityUtil.getCurrentUser();
        boolean isOwnerOfOrder = order.getUserEntity() != null
                && order.getUserEntity().getId().equals(currentUser.getId());
        if (!isOwnerOfOrder && currentUser.getRole() != UserRoles.ADMIN) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Order can no longer be canceled (status: " + order.getStatus() + ")");
        }
        order.setStatus(OrderStatus.CANCELED);
        return orderMapper.orderResponseDTO(ordersRepository.save(order), modelMapper);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId) {
        assertRestaurantAccess(restaurantId);
        return toDTOs(ordersRepository.findByRestaurantEntity_IdOrderByOrderDateDesc(restaurantId));
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        OrdersEntity order = findOrder(id);
        UserEntity currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() == UserRoles.RESTAURANT_OWNER) {
            assertRestaurantAccess(order.getRestaurantEntity().getId());
        }
        order.setStatus(status);
        if (status == OrderStatus.DELIVERED
                && order.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            order.setPaymentStatus(PaymentStatus.PAID);
        }
        return orderMapper.orderResponseDTO(ordersRepository.save(order), modelMapper);
    }

    @Override
    public Map<String, Object> getRestaurantStats(Long restaurantId) {
        assertRestaurantAccess(restaurantId);
        List<OrdersEntity> orders = ordersRepository.findByRestaurantEntity_IdOrderByOrderDateDesc(restaurantId);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", orders.size());
        stats.put("pendingOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count());
        stats.put("activeOrders", orders.stream().filter(o ->
                o.getStatus() == OrderStatus.CONFIRMED
                        || o.getStatus() == OrderStatus.PREPARING
                        || o.getStatus() == OrderStatus.READY_FOR_PICKUP
                        || o.getStatus() == OrderStatus.OUT_FOR_DELIVERY).count());
        stats.put("deliveredOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).count());
        stats.put("canceledOrders", orders.stream().filter(o -> o.getStatus() == OrderStatus.CANCELED).count());
        stats.put("totalRevenue", orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(OrdersEntity::getTotalAmount).sum());
        return stats;
    }

    @Override
    public List<OrderResponseDTO> getAvailableDeliveries() {
        return toDTOs(ordersRepository.findByStatusAndDeliveryAgentIsNull(OrderStatus.READY_FOR_PICKUP));
    }

    @Override
    public List<OrderResponseDTO> getMyDeliveries() {
        Long agentId = securityUtil.getCurrentUser().getId();
        return toDTOs(ordersRepository.findByDeliveryAgent_IdOrderByOrderDateDesc(agentId));
    }

    @Override
    public OrderResponseDTO acceptDelivery(Long orderId) {
        OrdersEntity order = findOrder(orderId);
        if (order.getDeliveryAgent() != null) {
            throw new IllegalStateException("Order already assigned to a delivery agent");
        }
        if (order.getStatus() != OrderStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Order is not ready for pickup (status: " + order.getStatus() + ")");
        }
        order.setDeliveryAgent(securityUtil.getCurrentUser());
        return orderMapper.orderResponseDTO(ordersRepository.save(order), modelMapper);
    }

    @Override
    public OrderResponseDTO updateDeliveryStatus(Long orderId, OrderStatus status) {
        OrdersEntity order = findOrder(orderId);
        UserEntity currentUser = securityUtil.getCurrentUser();
        boolean isAssignedAgent = order.getDeliveryAgent() != null
                && order.getDeliveryAgent().getId().equals(currentUser.getId());
        if (!isAssignedAgent && currentUser.getRole() != UserRoles.ADMIN) {
            throw new IllegalStateException("You are not assigned to this delivery");
        }
        if (status != OrderStatus.OUT_FOR_DELIVERY && status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivery agents can only mark orders OUT_FOR_DELIVERY or DELIVERED");
        }
        order.setStatus(status);
        if (status == OrderStatus.DELIVERED
                && order.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            order.setPaymentStatus(PaymentStatus.PAID);
        }
        return orderMapper.orderResponseDTO(ordersRepository.save(order), modelMapper);
    }

    private OrdersEntity findOrder(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    private void assertRestaurantAccess(Long restaurantId) {
        UserEntity currentUser = securityUtil.getCurrentUser();
        if (currentUser.getRole() == UserRoles.ADMIN) {
            return;
        }
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        if (restaurant.getOwner() == null || !restaurant.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("You do not own this restaurant");
        }
    }

    private List<OrderResponseDTO> toDTOs(List<OrdersEntity> orders) {
        return orders.stream()
                .map(order -> orderMapper.orderResponseDTO(order, modelMapper))
                .collect(Collectors.toList());
    }
}
