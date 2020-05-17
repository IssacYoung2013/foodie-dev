package com.issac.service;

import com.issac.pojo.Category;
import com.issac.pojo.vo.CategoryVO;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
public interface CategoryService {

    /**
     * 查询根级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 查询根分类下的分类
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
}
