package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eventCategory")
public class EventCategoryController {
    @Autowired
    private EventCategoryService eventCategoryService;



    @PostMapping("/create")
    public Result create(@RequestBody EventCategory eventCategory){
        eventCategoryService.save(eventCategory);
        HashMap<String, Object> map = new HashMap<>();
        map.put("eventCategory",eventCategory);
        return Result.success(map);
    }
    @GetMapping("list")
    public Result list(){
        LambdaQueryWrapper<EventCategory> lqw = new LambdaQueryWrapper<>();
        lqw.eq(EventCategory::getExist, true);
        List<EventCategory> list = eventCategoryService.list(lqw);
        ArrayList<String> nameList = new ArrayList<>();
        for (EventCategory eventCategory : list) {
            nameList.add(eventCategory.getCategoryName());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("eventCategoryName",nameList);
        return Result.success(map);
    }

}
