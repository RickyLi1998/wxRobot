package com.example.demo.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


public class Test{
    private  List<Class<Object>> list;

    public void setList(List<Class<Object>> list) {
        this.list = list;
    }

    public List<Class<Object>> getList() {
        return list;
    }
}
