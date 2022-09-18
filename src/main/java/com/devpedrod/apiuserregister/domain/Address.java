package com.devpedrod.apiuserregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "tb_address")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "disabled_at is null")
public class Address extends DomainEntity{

    @NotBlank(message = "Logradouro não pode ser em branco")
    @NotNull(message = "Logradouro não pode ser nulo")
    private String street;

    @NotNull(message = "Número do endereço não pode ser em branco")
    private Integer number;

    @NotBlank(message = "Cidade não pode ser em branco")
    @NotNull(message = "Cidade não pode ser nulo")
    private String city;

    @NotBlank(message = "Estado não pode ser em branco")
    @NotNull(message = "Estado não pode ser nulo")
    private String state;

    @NotBlank(message = "País não pode ser em branco")
    @NotNull(message = "País não pode ser nulo")
    private String country;

    @JsonIgnore
    @OneToOne
    private User user;
}
