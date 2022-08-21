package com.siripiri.example.hibernate.inheritance.tablePerClass;

import lombok.Data;

import javax.persistence.*;

/*
 * TABLE PER CLASS:
 *      It will create a table per class
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Mammal {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Integer bodyTemp;

    private String species;
}
