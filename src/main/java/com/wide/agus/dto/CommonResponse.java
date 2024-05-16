package com.wide.agus.dto;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private String message;
    private T data;

    public CommonResponse(){
        this.code= 200;
        this.message= "success";
    }

    public CommonResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
