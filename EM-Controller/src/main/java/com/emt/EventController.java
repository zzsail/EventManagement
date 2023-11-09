package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.EventComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventCategoryService eventCategoryService;

    private final Boolean IS_EXIST = true;
    private final Boolean IS_NOT_EXIST = false;

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
    @PostMapping(path = "/create")
    public Result saveEvent(@RequestBody EventComposite eventComposite){
        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Event::getEventName, eventComposite.getEventName());
        lqw.eq(Event::getExist, IS_EXIST);
        Event one = eventService.getOne(lqw);
        if (one != null){
            return Result.error("该赛事已存在");
        }
        Event event = new Event();
        event.setEventName(eventComposite.getEventName());
        event.setEventDate(eventComposite.getEventDate());
        event.setEventLocation(eventComposite.getEventLocation());
        event.setEventDescription(eventComposite.getEventDescription());
        String category = eventComposite.getCategoryName();
        LambdaQueryWrapper<EventCategory> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(EventCategory::getCategoryName, category);
        EventCategory one1 = eventCategoryService.getOne(lqw2);
        if(one1 == null){
            return Result.error("该分类不存在");
        }
        event.setCategoryId(one1.getCategoryId());
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
        event.setExist(IS_NOT_EXIST);
        event.setEventName(event.getEventName() + '$' + UUID.randomUUID()); //生成原赛事名＋uuid

        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Event::getEventId, eventId);
        eventService.update(event, lqw);
        return Result.success();
    }

    //查询赛事
    @GetMapping("/select")
    public Result select(String eventName){
        LambdaQueryWrapper<Event> lqw = new LambdaQueryWrapper<>();
        lqw.like(Event::getEventName, eventName);
        lqw.eq(Event::getExist, IS_EXIST);
        List<Event> events = eventService.list(lqw);
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("events", events);
        return Result.success(eventMap);
    }

}
