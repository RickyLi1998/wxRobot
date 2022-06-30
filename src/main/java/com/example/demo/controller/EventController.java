package com.example.demo.controller;

import com.example.demo.config.PluginList;
import com.example.demo.domain.ApiEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;


/**
 *  事件接收控制器
 * */
@RestController
@RequiredArgsConstructor
public class EventController {
    PluginList plugins;

    @PostMapping("/api")
    @ResponseBody
    public void api(ApiEvent event){
        plugins.handle(event);
    }
}
