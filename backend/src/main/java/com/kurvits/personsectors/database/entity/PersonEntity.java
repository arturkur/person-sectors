package com.kurvits.personsectors.database.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person")
public class PersonEntity extends AbstractEntity {

    @NonNull
    private String name;

    @NonNull
    private Boolean termsAccepted;

    @ManyToMany
    @JoinTable(
            name = "person_sector",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private Set<SectorEntity> selectedSectors;
}
