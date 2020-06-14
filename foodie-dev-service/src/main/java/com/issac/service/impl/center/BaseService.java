package com.issac.service.impl.center;

import com.github.pagehelper.PageInfo;
import com.issac.util.PagedGridResult;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-06-14
 * @desc:
 */
public class BaseService {

    protected PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setPage(page);
        gridResult.setRows(list);
        gridResult.setTotal(pageInfo.getPages());
        gridResult.setRecords(pageInfo.getTotal());
        return gridResult;
    }
}
