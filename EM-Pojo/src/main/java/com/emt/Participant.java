package com.emt;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//赛事参赛者信息表（Participants）实体类
@Data
public class Participant {
    @TableId(value = "participant_id", type = IdType.ASSIGN_ID)
    private Long participantId;//参赛者ID
    private Long userId;//用户ID
    private Long eventId;//赛事ID
    private String participantName;//参赛者姓名
    private String participantGender;//参赛者性别
    private Integer participantAge;//参赛者年龄
    private String participantContactInfo;//联系信息
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
