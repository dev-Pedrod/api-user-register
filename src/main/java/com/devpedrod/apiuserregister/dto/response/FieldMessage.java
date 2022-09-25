package com.devpedrod.apiuserregister.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage {
    private String fieldName;
    private String message;
}
