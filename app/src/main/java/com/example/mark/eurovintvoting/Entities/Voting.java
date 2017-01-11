package com.example.mark.eurovintvoting.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class Voting {
    int id;
    String name;
    boolean running;
}