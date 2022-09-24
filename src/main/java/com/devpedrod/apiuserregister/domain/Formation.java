package com.devpedrod.apiuserregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "tb_formation")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "disabled_at is null")
public class Formation extends DomainEntity {
    @NotBlank(message = "Nome não pode ser em branco")
    @NotNull(message = "Nome não pode ser nulo")
    private String name;
    @NotBlank(message = "Instituição não pode ser em branco")
    @NotNull(message = "Instituição não pode ser nulo")
    private String institution;
    @JsonIgnore
    @ManyToOne
    private User user;
}
