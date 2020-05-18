package com.issac.service.impl;

import com.issac.mapper.CategoryCustomMapper;
import com.issac.mapper.CategoryMapper;
import com.issac.pojo.Category;
import com.issac.pojo.vo.CategoryVO;
import com.issac.pojo.vo.NewItemsVO;
import com.issac.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    CategoryCustomMapper categoryCustomMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> result = categoryMapper.selectByExample(example);

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryCustomMapper.getSubCatList(rootCatId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String,Object> map = new HashMap<>(1);
        map.put("rootCatId",rootCatId);
        return categoryCustomMapper.getSixNewItemsLazy(map);
    }
}
