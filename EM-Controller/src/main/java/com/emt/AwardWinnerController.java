package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.AwardComposite;
import com.emt.composite.AwardWinnerComposite;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/awardWinner")
public class AwardWinnerController {
    @Autowired
    private AwardWinnerService awardWinnerService;

    @Autowired
    private AwardService awardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private EventService eventService;
    private final Boolean IS_EXIST = true;
    private final Boolean IS_NOT_EXIST = false;

    //分页查询
    @PostMapping("/awards")
    public Result awards(Long awardId, String username){
        Award award = awardService.getById(awardId);
        AwardWinnerComposite awardWinnerComposite = new AwardWinnerComposite();
        awardWinnerComposite.setAwardName(award.getAwardName());
        awardWinnerComposite.setAwardId(awardId);
        awardWinnerComposite.setAwardDate(LocalDateTime.now());
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getExist,IS_EXIST);
        lqw.eq(User::getUsername,username);
        User user = userService.getOne(lqw);
        if(user == null) return Result.error("该用户不存在");
        LambdaQueryWrapper<Participant> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Participant::getExist,IS_EXIST);
        lqw2.eq(Participant::getUserId,user.getUserId());
        Participant participant = participantService.getOne(lqw2);
        if(participant == null) return Result.error("该用户没有参赛");
        awardWinnerComposite.setParticipantId(participant.getParticipantId());
        awardWinnerComposite.setParticipantName(participant.getParticipantName());
        awardWinnerComposite.setExist(IS_EXIST);
        AwardWinner awardWinner = awardWinnerComposite;
        awardWinnerService.save(awardWinner);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize){
        Page<AwardWinner> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<AwardWinner> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AwardWinner::getExist,IS_EXIST);
        List<AwardWinner> records = awardWinnerService.page(page, lqw).getRecords();
        List<AwardWinnerComposite> newRecords = new ArrayList<>();
        records.stream().forEach( item -> {
            AwardWinnerComposite awardWinnerComposite = new AwardWinnerComposite();
            awardWinnerComposite.setWinnerId(item.getWinnerId());
            awardWinnerComposite.setAwardId(item.getAwardId());
            awardWinnerComposite.setParticipantId(item.getParticipantId());
            awardWinnerComposite.setAwardDate(item.getAwardDate());
            awardWinnerComposite.setExist(item.getExist());
            awardWinnerComposite.setAwardName(awardService.getById(item.getAwardId()).getAwardName());
            awardWinnerComposite.setParticipantName(participantService.getById(item.getParticipantId()).getParticipantName());
            awardWinnerComposite.setEventName(eventService.getById(awardService.getById(item.getAwardId()).getEventId()).getEventName());
            newRecords.add(awardWinnerComposite);
        });
        Page<AwardWinnerComposite> newPage = new Page<>();
        BeanUtils.copyProperties(page,newPage);
        newPage.setRecords(newRecords);
        Map<String,Object> map = new HashMap<>();
        map.put("items",newPage);
        return Result.success(map);


    }

}
