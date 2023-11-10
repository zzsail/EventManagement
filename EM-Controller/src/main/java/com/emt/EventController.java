package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.EventComposite;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private RatingService ratingService;

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
        List<Event> records = eventService.page(pages, lqw).getRecords();
        List<EventComposite> recordsComposite = new ArrayList<>();
        records.stream().forEach(item -> {
            LambdaQueryWrapper<Rating> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(Rating::getEventId, item.getEventId());
            List<Rating> list = ratingService.list(lqw3);
            BigDecimal ratingValue = null;
            for (Rating rating : list) {
                ratingValue.add(rating.getRatingValue());
            }
            ratingValue.divide(BigDecimal.valueOf(list.size()));
            EventComposite eventComposite = setAttribute(item);
            eventComposite.setRatingValue(ratingValue);
            recordsComposite.add(eventComposite);
        });
        Page<EventComposite> newPages = new Page<>();
        BeanUtils.copyProperties(pages,newPages);
        newPages.setRecords(recordsComposite);


        Map<String, Object> map = new HashMap<>();
        map.put("items", newPages);
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
        Event event = eventComposite;
        String category = eventComposite.getCategoryName();
        LambdaQueryWrapper<EventCategory> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(EventCategory::getCategoryName, category);
        EventCategory one1 = eventCategoryService.getOne(lqw2);
        if(one1 == null){
            return Result.error("该分类不存在");
        }
        event.setCategoryId(one1.getCategoryId());
        eventService.save(event);
        if(LocalDate.now().isBefore(eventComposite.getEventDate())) {
            eventComposite.setStatus(true);
        }
        else {
            eventComposite.setStatus(false);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("event",eventComposite);
        return Result.success(map);
    }

    //修改赛事信息
    @Transactional
    @PutMapping("/update")
    public Result update(@RequestBody Event event){
        try {
            eventService.updateById(event);
        } catch (Exception e) {
            return Result.error("赛事已存在");
        }
        EventComposite eventComposite = setAttribute(event);

        HashMap<String, Object> map = new HashMap<>();
        map.put("event", eventComposite);
        return Result.success(map);
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

    public EventComposite setAttribute(Event item) {
        EventComposite eventComposite = new EventComposite();
        eventComposite.setEventId(item.getEventId());
        eventComposite.setEventDate(item.getEventDate());
        eventComposite.setEventDescription(item.getEventDescription());
        eventComposite.setEventLocation(item.getEventLocation());
        eventComposite.setEventName(item.getEventName());
        eventComposite.setCategoryId(item.getCategoryId());
        eventComposite.setCategoryName(eventCategoryService.getById(item.getCategoryId()).getCategoryName());
        if(LocalDate.now().isBefore(item.getEventDate())) {
            eventComposite.setStatus(true);
        }
        else {
            eventComposite.setStatus(false);
        }
        return eventComposite;
    }

}
