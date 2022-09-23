package com.devpedrod.apiuserregister.dto;

import com.devpedrod.apiuserregister.domain.enums.Status;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusDto {
    @NotNull(message = "Status não pode ser nulo")
    private Status status;
    @NotNull(message = "Id do usuário não pode ser nulo")
    private Long userId;

    public String toJson(){
        return "{\"status\" : \"" + status + "\",\"userId\" :\"" + userId+"\"}";
    }
}
