package com.kurvits.personsectors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    private UUID id;
    private String name;
    private List<Sector> children;
}
