package com.siripiri.example.hibernate.inheritance.joinedTable;

import lombok.Data;

import javax.persistence.*;

/*
 * JOINED TABLE:
 *      It will join the tables by foreign constrains and for search query it will use inner join to search the query
 *    Example:
 *      select
 *          electric_guitar.id, guitar.number_of_strings, electric_guitar.number_of_pickups
 *      from
 *          electric_guitar
 *      inner join
 *          guitar on
 *              guitar.id = electric_guitar.id
 *      inner join
 *          instruments on
 *              instruments.id = electric_guitar.id
 *
 *      Example for insert query:
 *      insert into
 *          instruments
 *      values ();
 *      insert into
 *          guitar
 *              (number_of_strings, id)
 *          values
 *              (10, (select max(id) from instruments));
 *      insert into
 *          electric_guitar
 *              (number_of_pickups, id)
 *          values
 *              (10, (select max(id) from guitar));
 */
@Entity
@Inheritance( strategy = InheritanceType.JOINED)
@Data
public abstract class Instruments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
}
