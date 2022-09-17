package com.devpedrod.apiuserregister.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "disabled_at is null")
public class User extends DomainEntity {
    private String nome;
    private String cpf;

    @OneToMany(mappedBy = "user")
    private List<Formation> formations = new ArrayList<>();
    @OneToOne
    private Address address;
    @ManyToMany(mappedBy = "users")
    private List<Permissions> permissions = new ArrayList<>();
}
