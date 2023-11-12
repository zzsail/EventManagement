package com.emt.composite;

import com.emt.Event;
import com.emt.Participant;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class EventComposite extends Event {
    private String categoryName;
    private BigDecimal ratingValue;
    private Integer participantNum;
    private Boolean status;
}
