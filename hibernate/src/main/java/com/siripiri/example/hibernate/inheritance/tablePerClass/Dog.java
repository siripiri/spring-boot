package com.siripiri.example.hibernate.inheritance.tablePerClass;

import javax.persistence.Entity;

@Entity
public class Dog extends Mammal{
    private String breed;
}
