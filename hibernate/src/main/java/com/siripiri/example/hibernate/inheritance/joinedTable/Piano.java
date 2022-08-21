package com.siripiri.example.hibernate.inheritance.joinedTable;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Piano extends Instruments{
    private Integer numberOfKeys;
}
