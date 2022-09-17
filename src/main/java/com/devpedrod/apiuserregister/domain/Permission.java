package com.devpedrod.apiuserregister.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_permissions")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "disabled_at is null")
public class Permission extends DomainEntity {
    private String name;
    private String permission;
    @JsonIgnore
    @ManyToMany()
    private List<User> users = new ArrayList<>();
}