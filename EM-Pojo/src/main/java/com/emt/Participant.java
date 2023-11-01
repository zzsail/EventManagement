package com.emt;

import com.sun.jdi.IntegerType;
import lombok.Data;

//赛事参赛者信息表（Participants）实体类
@Data
public class Participant {
    private Long participantId;
    private Event event;
    private String participantName;
    private Integer participantAge;
    private String participantTeam;
    private String participantContactInfo;
}
