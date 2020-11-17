package com.issac.es.service;

import com.issac.util.PagedGridResult;

/**
 * @author: ywy
 * @date: 2020-11-15
 * @desc:
 */
public interface ItemsEsService {
    /**
     * 搜索
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords,
                                String sort,
                                Integer page,
                                Integer pageSize);
}
