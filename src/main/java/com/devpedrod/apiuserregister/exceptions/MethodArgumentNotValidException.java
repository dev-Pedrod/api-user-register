package com.devpedrod.apiuserregister.exceptions;

import com.devpedrod.apiuserregister.dto.response.FieldMessage;
import lombok.Getter;

import java.util.List;

@Getter
public class MethodArgumentNotValidException extends RuntimeException{

    private final List<FieldMessage> messages;
    public MethodArgumentNotValidException(List<FieldMessage> messages) {
        this.messages = messages;
    }
}
