package com.siripiri.example.hibernate.inheritance.tablePerClass;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Dolphin extends Mammal{
    private Boolean hasSpots;
}
