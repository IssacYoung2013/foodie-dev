package com.issac.mapper;


import com.issac.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryCustomMapper {

    List<CategoryVO> getSubCatList(Integer rootCatId);

    List getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);
}