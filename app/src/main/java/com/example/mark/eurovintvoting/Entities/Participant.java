package com.example.mark.eurovintvoting.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class Participant{
    Integer id;
    String artist;
    String title;
    String country;
}