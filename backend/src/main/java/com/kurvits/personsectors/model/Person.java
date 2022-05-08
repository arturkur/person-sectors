package com.kurvits.personsectors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @AssertTrue
    private Boolean termsAccepted;

    @NotNull
    @NotEmpty
    private List<UUID> selectedSectors;
}
