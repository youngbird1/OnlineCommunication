package com.ct.websocket.controller;

import com.ct.websocket.entity.ApiReturnObject;
import com.ct.websocket.service.WebSocketServer;
import com.ct.websocket.utils.ApiReturnUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @auther:chent69
 * @date: 2019/12/16-16 :34
 */
@RestController
@RequestMapping("/checkcenter")
public class CheckCenterController {
    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @RequestMapping("/socket/push/{cid}")
    public ApiReturnObject pushToWeb(@PathVariable String cid, @RequestParam String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiReturnUtil.error("cid:" + cid+"#"+e.getMessage());
        }
        return ApiReturnUtil.success("cid:" + cid);
    }
//
//    //推送数据接口
//    @GetMapping("/socket/push")
//    public ApiReturnObject pushToAllWeb(@PathVariable String cid, @RequestParam String message) {
//        try {
//            WebSocketServer.sendInfo(message,cid);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ApiReturnUtil.error("cid:" + cid+"#"+e.getMessage());
//        }
//        return ApiReturnUtil.success("cid:" + cid);
//    }
}
