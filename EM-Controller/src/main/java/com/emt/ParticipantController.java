package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.ParticipantComposite;
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
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    private final Boolean IS_EXIST = true;

    private final Boolean IS_NOT_EXIST = false;

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
    @PostMapping(path = "/create")
    public Result saveParticipant(@RequestBody ParticipantComposite participantComposite){
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        //根据参赛者联系方式是否存在判断参赛者是否存在
        lqw.eq(Participant::getParticipantContactInfo, participantComposite.getParticipantContactInfo());
        lqw.eq(Participant::getExist, IS_EXIST);
        Participant one = participantService.getOne(lqw);
        if(one != null){
            return Result.error("该参赛者已存在");
        }
        Participant participant = new Participant();
        participant.setParticipantName(participantComposite.getParticipantName());
        participant.setParticipantAge(participantComposite.getParticipantAge());
        participant.setParticipantGender(participantComposite.getParticipantGender());
        participant.setParticipantContactInfo(participantComposite.getParticipantContactInfo());
        LambdaQueryWrapper<Event> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Event::getEventName, participantComposite.getEventName());
        lqw2.eq(Event::getExist, IS_EXIST);
        Event one2 = eventService.getOne(lqw2);
        if (one2 == null){
            return Result.error("该赛事不存在");
        }
        participant.setEventId(one2.getEventId());
        participantService.save(participant);
        return Result.success();

    }
    //修改参赛者
    @Transactional
    @PutMapping("/update")
    public Result update(@RequestBody Participant participant){
        try {
            participantService.updateById(participant);
        }catch (Exception e) {
            return Result.error("参赛者已存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("participant", participant);
        return Result.success(map);
    }

    @PostMapping("/delete")
    public Result delete(Long participantId){
        Participant participant = participantService.getById(participantId);
        if (participant == null){
            return Result.error("参赛者不存在");
        }
        participant.setExist(IS_NOT_EXIST);
        //生成原联系信息+uuid
        participant.setParticipantContactInfo(participant.getParticipantContactInfo() + '$' + UUID.randomUUID());
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Participant::getParticipantId, participantId);
        participantService.update(participant, lqw);
        return Result.success();
    }

    //查询参赛者
    @GetMapping("/select")
    public Result select(String participantName){
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        lqw.like(Participant::getParticipantName, participantName);
        lqw.eq(Participant::getExist, IS_EXIST);
        List<Participant> participants = participantService.list(lqw);
        Map<String, Object> participantMap = new HashMap<>();
        //将查询到的数据存入map中
        participantMap.put("participants", participants);
        return Result.success(participantMap);
    }


}
