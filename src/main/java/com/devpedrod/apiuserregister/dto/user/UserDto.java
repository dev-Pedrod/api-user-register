package com.devpedrod.apiuserregister.dto.user;

import com.devpedrod.apiuserregister.dto.DomainDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserDto extends DomainDto {
    @NotNull(message = "O nome não pode ser nulo")
    private String name;

    @NotNull(message = "O CPF não pode ser em nulo")
    private String cpf;
}
