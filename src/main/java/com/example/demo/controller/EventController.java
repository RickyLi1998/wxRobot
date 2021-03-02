package com.example.demo.controller;

import com.example.demo.config.PluginList;
import com.example.demo.domain.ApiEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;


/**
 *  事件接收控制器
 * */
@RestController
public class EventController {
    @Autowired
    PluginList plugins;

    @PostMapping("/api")
    @ResponseBody
    public void api(ApiEvent event){
        try {
            plugins.handle(event);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
