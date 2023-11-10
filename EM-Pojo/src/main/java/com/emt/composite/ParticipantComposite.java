package com.emt.composite;

import com.emt.Participant;
import lombok.Data;

@Data
public class ParticipantComposite extends Participant {
    private String eventName;

    private String userName;
}
