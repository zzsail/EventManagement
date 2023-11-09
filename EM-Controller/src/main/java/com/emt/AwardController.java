package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

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
}
