package com.siripiri.example.hibernate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@NamedQuery(name = "driver_find_all", query = "FROM Driver")  // this is for NamedQuery (Topic: Hibernate Query)
@NamedQueries({
        @NamedQuery(name = "driver_find_all", query = "FROM Driver"),
        @NamedQuery(name = "find_by_name", query = "FROM Driver a WHERE a.firstName = :first_name and a.lastName = :last_name")
})
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String firstName;

    public String lastName;
}
