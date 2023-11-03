package com.emt;

import lombok.Data;

@Data
public class Result {
    private Object data;
    private Integer code;
    private String msg;
}
