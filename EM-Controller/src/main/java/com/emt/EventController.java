package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    private String IS_EXIST = "1";

    //分页查询
    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize, String eventName){
        Page<Event> pages = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(eventName), Event::getEventName, eventName);
        lqw.eq(Event::getExist, IS_EXIST);
        lqw.orderByDesc(Event::getEventDate);
        eventService.page(pages, lqw);
        Map<String, Object> map = new HashMap<>();
        map.put("items", pages);
        return Result.success(map);
    }
}
