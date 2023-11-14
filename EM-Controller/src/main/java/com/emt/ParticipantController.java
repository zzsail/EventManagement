package com.emt;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.ParticipantComposite;
import jakarta.servlet.http.Part;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    private final Boolean IS_EXIST = true;

    private final Boolean IS_NOT_EXIST = false;

    //分页查询
    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize, String participantName){
        Page<Participant> pages = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Participant> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(participantName), Participant::getParticipantName, participantName);
        lqw.eq(Participant::getExist, IS_EXIST);
        List<Participant> records = participantService.page(pages, lqw).getRecords();
        List<ParticipantComposite> recordsComposite = new ArrayList<>();
        records.stream().forEach(item -> {
            //根据用户id获取用户名
            ParticipantComposite participantComposite = setAttribute(item);

            recordsComposite.add(participantComposite);
        });
        Page<ParticipantComposite> newPages = new Page<>();
        BeanUtils.copyProperties(pages, newPages);
        newPages.setRecords(recordsComposite);

        Map<String, Object> map = new HashMap<>();
        map.put("items", newPages);
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
        Participant participant = participantComposite;
        //根据赛事名获取赛事id
        LambdaQueryWrapper<Event> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Event::getEventName, participantComposite.getEventName());
        lqw2.eq(Event::getExist, IS_EXIST);
        Event one2 = eventService.getOne(lqw2);
        if (one2 == null){
            return Result.error("该赛事不存在");
        }
        participant.setEventId(one2.getEventId());
        //根据用户名获取用户id
        LambdaQueryWrapper<User> lqw3 = new LambdaQueryWrapper<>();
        lqw3.eq(User::getUsername, participantComposite.getUsername());
        lqw3.eq(User::getExist, IS_EXIST);
        User one1 = userService.getOne(lqw3);
        if (one1 == null){
            return Result.error("该用户不存在");
        }
        participant.setUserId(one1.getUserId());
        participantService.save(participant);
        participantComposite = setAttribute(participant);
        Map<String, Object> map = new HashMap<>();
        map.put("participant", participantComposite);
        return Result.success(map);

    }
    //修改参赛者
    @Transactional
    @PutMapping("/update")
    public Result update(@RequestBody Participant participantComposite){
        Participant participant = participantComposite;
        try {
            participantService.updateById(participant);
        }catch (Exception e) {
            return Result.error("参赛者已存在");
        }
        participantComposite = setAttribute(participant);
        Map<String, Object> map = new HashMap<>();
        map.put("participant", participantComposite);
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

    public ParticipantComposite setAttribute(Participant item){
        ParticipantComposite participantComposite = new ParticipantComposite();
        participantComposite.setEventId(item.getEventId());
        participantComposite.setParticipantId(item.getParticipantId());
        participantComposite.setUserId(item.getUserId());
        participantComposite.setParticipantName(item.getParticipantName());
        participantComposite.setParticipantGender(item.getParticipantGender());
        participantComposite.setParticipantAge(item.getParticipantAge());
        participantComposite.setParticipantContactInfo(item.getParticipantContactInfo());
        participantComposite.setExist(item.getExist());
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(User::getUserId, item.getUserId());
        lqw2.eq(User::getExist, IS_EXIST);
        User one = userService.getOne(lqw2);

        participantComposite.setUsername(one.getUsername());
        //根据赛事id获取赛事名
        LambdaQueryWrapper<Event> lqw3 = new LambdaQueryWrapper<>();
        lqw3.eq(Event::getEventId, item.getEventId());
        lqw3.eq(Event::getExist, IS_EXIST);
        Event one1 = eventService.getOne(lqw3);
        participantComposite.setEventName(one1.getEventName());
        return participantComposite;
    }


}
