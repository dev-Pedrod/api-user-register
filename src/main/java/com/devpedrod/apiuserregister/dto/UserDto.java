package com.devpedrod.apiuserregister.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserDto extends DomainDto{
    private String name;
    private String cpf;
}
