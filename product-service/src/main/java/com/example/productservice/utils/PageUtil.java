package com.example.productservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtil {

    /**
     * 페이지 정보 없을 시, Default page 리턴
     *
     * @param page 페이지
     * @return int
     */
    public static int getPage(Integer page) {
        return page == null ? 1 : page;
    }

    /**
     * 페이지 사이즈 정보 없을 시, Deafult page size 리턴
     *
     * @param pageSize 페이지사이즈
     * @return int
     */
    public static int getPageSize(Integer pageSize) {
        return pageSize == null ? 20 : pageSize;
    }
}
