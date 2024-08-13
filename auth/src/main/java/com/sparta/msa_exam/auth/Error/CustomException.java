package com.sparta.msa_exam.auth.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null){
            return errorCode.getMessage();
        }

        return String.format("%s. %s",errorCode.getMessage(), message);
    }
}
