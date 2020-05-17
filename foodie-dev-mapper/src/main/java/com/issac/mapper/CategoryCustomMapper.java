package com.issac.mapper;


import com.issac.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryCustomMapper {

    List<CategoryVO> getSubCatList(Integer rootCatId);
}