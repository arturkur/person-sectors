package com.kurvits.personsectors.database.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sector")
public class SectorEntity extends AbstractEntity {
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SectorEntity parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("name ASC")
    private Set<SectorEntity> children;
}
