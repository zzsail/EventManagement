package com.emt;


import lombok.Data;

//赛事参赛者信息表（Participants）实体类
@Data
public class Participant {
    private Long participantId;
    private Long eventId;
    private String participantName;
    private Integer participantAge;
    private String participantTeam;
    private String participantContactInfo;
    private Boolean exist;
}
