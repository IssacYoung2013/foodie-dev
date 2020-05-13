package com.issac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@ApiIgnore
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello World~";
    }
}
