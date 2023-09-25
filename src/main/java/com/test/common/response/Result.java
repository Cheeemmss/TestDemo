package com.test.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.test.common.response.CodeConstance.CODE_200;

/**
 * @Author cheems
 * @Date 2023/9/22 15:06
 */

@Data
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;


    public static Result success(String msg){
        return new Result(CODE_200,msg,null);
    }

    public static Result success(String msg,Object data){
        return new Result(CODE_200,msg,data);
    }
    public static Result success(Object data){
        return new Result(CODE_200,null,data);
    }

    public static Result fail(String Code,String msg){
        return new Result(Code,msg,null);
    }
}
