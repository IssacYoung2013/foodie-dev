package com.issac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
@RestController
@ApiIgnore
public class TempResultController {

    @GetMapping("/alipayResult")
    public String alipayResult(HttpServletRequest request, HttpServletResponse response) {

        return "alipayResult";
    }
}