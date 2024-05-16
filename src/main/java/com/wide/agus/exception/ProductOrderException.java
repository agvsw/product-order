package com.wide.agus.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderException extends Exception {
    private int code;
    private String message;

    public ProductOrderException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
