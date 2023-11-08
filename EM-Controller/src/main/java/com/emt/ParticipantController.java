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
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    private String IS_EXIST = "1";

    //分页查询
    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize, String participantName){
        Page<Participant> pages = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(participantName), Participant::getParticipantName, participantName);
        lqw.eq(Participant::getExist, IS_EXIST);
        participantService.page(pages, lqw);
        Map<String, Object> map = new HashMap<>();
        map.put("items", pages);
        return Result.success(map);
    }

    //添加参赛者
    @PostMapping(path = "/save")
    public Result saveParticipant(@RequestBody Participant participant){
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        //根据参赛者联系方式是否存在判断参赛者是否存在
        lqw.eq(Participant::getParticipantContactInfo, participant.getParticipantContactInfo());
        Participant one = participantService.getOne(lqw);
        if(one != null){
            return Result.error("该参赛者已存在");
        }
        participantService.save(participant);
        return Result.success();

    }



}
