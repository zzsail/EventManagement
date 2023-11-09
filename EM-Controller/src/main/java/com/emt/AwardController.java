package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.AwardComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @Autowired
    private EventService eventService;

    private final Boolean IS_EXIST = true;
    private final Boolean IS_NOT_EXIST = false;


    //分页查询
    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize, String awardName){
        Page<Award> pages = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Award> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(awardName), Award::getAwardName, awardName);
        lqw.eq(Award::getExist, IS_EXIST);
        awardService.page(pages, lqw);
        Map<String, Object> map = new HashMap<>();
        map.put("items", pages);
        return Result.success(map);
    }

    //添加奖项
    @PostMapping(path = "/create")
    public Result saveAward(@RequestBody AwardComposite awardComposite){
        LambdaQueryWrapper<Award> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Award::getAwardName, awardComposite.getEventName());
        lqw.eq(Award::getExist, IS_EXIST);
        Award one = awardService.getOne(lqw);
        if (one != null){
            return Result.error("该奖项已存在");
        }
        Award award = new Award();
        award.setAwardName(awardComposite.getAwardName());
        award.setAwardDescription(awardComposite.getAwardDescription());
        String eventName = awardComposite.getEventName();
        LambdaQueryWrapper<Event> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Event::getEventName, eventName);
        lqw2.eq(Event::getExist, IS_EXIST);
        Event one1 = eventService.getOne(lqw2);
        if(one1 == null){
            return Result.error("该赛事不存在");
        }
        award.setEventId(one1.getEventId());
        awardService.save(award);
        return Result.success();

    }
}
