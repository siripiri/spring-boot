package com.siripiri.example.hibernate.inheritance.mappedSuperClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends AbstractData{
    public Long noOfLorry;
}
