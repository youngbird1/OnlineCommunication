package com.ct.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @OriginalAuthor:
 * @Author: ChenT
 * @Date: 2019/12/17 19:16
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    String hello(){
        return "hello";
    }
}
