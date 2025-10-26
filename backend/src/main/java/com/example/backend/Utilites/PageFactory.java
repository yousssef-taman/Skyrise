package com.example.backend.Utilites;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageFactory {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");

    private PageFactory() {}

    public static Pageable create(int pageNumber, Integer pageSize, Sort sort) {
        int validSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        Sort effectiveSort = (sort == null) ? DEFAULT_SORT : sort;
        return PageRequest.of(pageNumber, validSize, effectiveSort);
    }
}
