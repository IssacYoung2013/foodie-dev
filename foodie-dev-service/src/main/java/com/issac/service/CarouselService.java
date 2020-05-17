package com.issac.service;


import com.issac.pojo.Carousel;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
