package com.food.fooddeliverybackend.util;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PaginationUtility {
    public static Pageable applyPagination(final PageModel pageModel) {
        if (Objects.isNull(pageModel.getRecordSize()) || pageModel.getRecordSize() == 0) {
            pageModel.setRecordSize(2);
        }
        if (Objects.isNull(pageModel.getPageNumber())) {
            pageModel.setPageNumber(0);
        }
        Pageable pageable = null;
        if (StringUtils.isBlank(pageModel.getSortColumn())) {
            pageable = PageRequest.of(pageModel.getPageNumber(), pageModel.getRecordSize(),
                    Sort.by("createdAt").descending());
        } else if (pageModel.getOrderBy() == OrderBy.DESC) {
            pageable = PageRequest.of(pageModel.getPageNumber(), pageModel.getRecordSize(),
                    Sort.by(pageModel.getSortColumn()).descending());
        } else if (pageModel.getOrderBy() == OrderBy.ASC) {
            pageable = PageRequest.of(pageModel.getPageNumber(), pageModel.getRecordSize(),
                    Sort.by(pageModel.getSortColumn()));
        } else {
            pageable = PageRequest.of(0, 2, Sort.by("createdAt").descending());
        }
        return pageable;
    }
}
