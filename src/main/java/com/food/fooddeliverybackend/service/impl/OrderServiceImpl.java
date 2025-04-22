package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.OrdersEntity;
import com.food.fooddeliverybackend.mapper.OrderMapper;
import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.OrdersRepository;
import com.food.fooddeliverybackend.service.OrderService;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrdersRepository ordersRepository, OrderMapper orderMapper, ModelMapper modelMapper) {
        this.ordersRepository = ordersRepository;
        this.orderMapper = orderMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        OrdersEntity orders = new OrdersEntity();
        BeanUtils.copyProperties(orderRequestDTO, orders);
        OrdersEntity savedOrders = ordersRepository.save(orders);
        return orderMapper.orderResponseDTO(savedOrders,modelMapper);
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        OrdersEntity orders = ordersRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(orderRequestDTO, orders);
        OrdersEntity savedOrders = ordersRepository.save(orders);
        return orderMapper.orderResponseDTO(savedOrders,modelMapper);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        OrdersEntity orders = ordersRepository.findById(id).orElse(null);
        return orderMapper.orderResponseDTO(orders,modelMapper);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(PageModel pageModel) {
        Page<OrdersEntity> orders = ordersRepository.findAll(applyPagination(pageModel));
        return orders.stream().map(ordersEntity ->  orderMapper.orderResponseDTO(ordersEntity, modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        OrdersEntity orders = ordersRepository.findById(id).orElse(null);
        ordersRepository.delete(orders);
    }
}
