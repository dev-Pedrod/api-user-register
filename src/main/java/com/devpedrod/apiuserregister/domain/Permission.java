package com.devpedrod.apiuserregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_permissions")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "disabled_at is null")
public class Permission extends DomainEntity {
    @NotBlank(message = "Nome não pode ser em branco")
    @NotNull(message = "Nome não pode ser nulo")
    private String name;

    @NotBlank(message = "Permissão  não pode ser em branco")
    @NotNull(message = "Permissão não pode ser nulo")
    private String permission;

    @JsonIgnore
    @ManyToMany
    private Set<User> users = new HashSet<>();
}