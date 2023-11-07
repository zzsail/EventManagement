package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    //添加赛事
    @PostMapping(path = "/save")
    public Result saveEvent(@RequestBody Event event){
        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Event::getEventName, event.getEventName());
        if(eventService.getOne(lqw) != null){
            return Result.error("赛事名已存在");
        }
        eventService.save(event);
        return Result.success();
    }

    //修改赛事信息
    @Transactional
    @PutMapping("/update")
    public Result update(@RequestBody Event event){
        try {
            eventService.updateById(event);
        } catch (Exception e) {
            return Result.error("赛事Id已存在");
        }
        return Result.success();
    }

    //删除赛事
    @PostMapping("/delete")
    public Result delete(Long eventId){
        Event event = eventService.getById(eventId);
        if(event == null){
            return Result.error("该赛事不存在");
        }
        event.setExist(false);

        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Event::getEventId, eventId);
        eventService.update(event, lqw);
        return Result.success();
    }
}
