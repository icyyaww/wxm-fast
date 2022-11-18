/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.wxm.msfast.base.common.utils;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */

@Data
public class PageResult<T> {

    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int currPage;
    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 分页
     */
    public PageResult(Page<T> page) {
        if (page != null) {
            this.rows = page.getResult();
            this.totalCount = page.getTotal();
            this.pageSize = page.getPageSize();
            this.currPage = page.getPageNum();
            this.totalPage = page.getPages();
        }
    }

}
