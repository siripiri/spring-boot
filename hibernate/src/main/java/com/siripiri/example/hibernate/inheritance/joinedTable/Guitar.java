package com.siripiri.example.hibernate.inheritance.joinedTable;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Guitar extends Instruments{
    private Integer numberOfStrings;
}
