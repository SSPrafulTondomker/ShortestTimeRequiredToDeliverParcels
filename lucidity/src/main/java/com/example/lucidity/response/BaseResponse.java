package com.example.lucidity.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseResponse implements Serializable {

    private int code;
    private String message;
    private String customErrorCode;
    private String customErrorMessage;
    private Object Data;
}