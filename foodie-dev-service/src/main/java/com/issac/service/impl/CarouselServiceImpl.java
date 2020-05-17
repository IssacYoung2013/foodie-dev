package com.issac.service.impl;

import com.issac.mapper.CarouselMapper;
import com.issac.pojo.Carousel;
import com.issac.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    CarouselMapper carouselMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);

        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
