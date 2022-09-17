package com.devpedrod.apiuserregister.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "tb_formation")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "disabled_at is null")
public class Formation extends DomainEntity {
    private String name;
    private String institution;
    @ManyToOne
    private User user;
}
