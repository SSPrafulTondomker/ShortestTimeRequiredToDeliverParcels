package com.example.lucidity.response;


import com.example.lucidity.enumerations.Status;

public class SuccessResponse extends ApiResponse{
    public SuccessResponse(Object data, String message, int code) {
        super(Status.SUCCESS);
        setCode(code);
        setData(data);
        setMessage(message);
    }
}