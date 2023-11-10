package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emt.composite.AwardWinnerComposite;
import com.mysql.cj.jdbc.ha.LoadBalancedMySQLConnection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.rmi.MarshalledObject;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/awardWinner")
public class AwardWinnerController {
    @Autowired
    private AwardWinnerService awardWinnerService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private AwardService awardService;

    private final Boolean IS_EXIST = true;
    private final Boolean IS_NOT_EXIST = false;

    //分页查询
    @GetMapping("/page")
    public Result page(Integer pageNum, Integer pageSize, Long awardId){
        Page<AwardWinner> pages = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AwardWinner> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(awardId), AwardWinner::getAwardId, awardId);
        lqw.eq(AwardWinner::getExist, IS_EXIST);
        List<AwardWinner> records = awardWinnerService.page(pages, lqw).getRecords();
        List<AwardWinnerComposite> recordsComposite = new ArrayList<>();
        records.stream().forEach(item -> {
            AwardWinnerComposite awardWinnerComposite = new AwardWinnerComposite();
            awardWinnerComposite.setAwardDate(item.getAwardDate());
            //根据参赛者id获取参赛者姓名
            LambdaQueryWrapper<Participant> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(Participant::getParticipantId, item.getParticipantId());
            lqw2.eq(Participant::getExist, IS_EXIST);
            Participant one = participantService.getOne(lqw2);
            awardWinnerComposite.setParticipantName(one.getParticipantName());
            //根据奖项id获取奖项名
            LambdaQueryWrapper<Award> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(Award::getAwardId, item.getAwardId());
            lqw3.eq(Award::getExist, IS_EXIST);
            Award one1 = awardService.getOne(lqw3);
            awardWinnerComposite.setAwardName(one1.getAwardName());
            recordsComposite.add(awardWinnerComposite);
        });
        Page<AwardWinnerComposite> newPages = new Page<>();
        BeanUtils.copyProperties(pages, newPages);
        newPages.setRecords(recordsComposite);

        Map<String, Object> map = new HashMap<>();
        map.put("items", newPages);
        return Result.success(map);
    }

    @PostMapping(path = "/create")
    public Result saveAwardWinner(@RequestBody Participant participant){
        return null;
    }


}
