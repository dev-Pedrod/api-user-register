package com.devpedrod.apiuserregister.domain;

import com.devpedrod.apiuserregister.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tb_user")
@Getter @Setter
@AllArgsConstructor
@Where(clause = "disabled_at is null")
public class User extends DomainEntity {
    @NotNull(message = "O nome não pode ser nulo")
    private String name;

    @NotNull(message = "O CPF não pode ser em nulo")
    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Formation> formations = new ArrayList<>();

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>();

    public User(){
        super();
        this.status = Status.ACTIVE;
    }
}
