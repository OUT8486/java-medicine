package com.example.spring_boot.controller;

import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisUtils.set(key, value);
        return "设置成功";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisUtils.get(key);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        redisUtils.delete(key);
        return "删除成功";
    }
}
