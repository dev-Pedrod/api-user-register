package com.devpedrod.apiuserregister.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "disabled_at is null")
public class User extends DomainEntity {
    @NotNull(message = "O nome não pode ser nulo")
    private String name;
    @NotNull(message = "O CPF não pode ser em nulo")
    private String cpf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Formation> formations = new ArrayList<>();
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();
}
