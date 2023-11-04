package com.emt;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private Map<String, Object> data;
    private Integer code;
    private String message;

    public static Result success(Map<String, Object> map){
        Result r = new Result();
        r.code = Code.OK;
        r.data = map;
        return r;
    }

    public static Result success(){
        Result r = new Result();
        r.code = Code.OK;
        r.data = new HashMap<>();
        return r;
    }

    public static Result error(String msg){
        Result r = new Result();
        r.code = Code.ERR;
        r.message = msg;
        return r;
    }


}
