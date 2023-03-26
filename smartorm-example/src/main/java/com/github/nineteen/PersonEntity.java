package com.github.nineteen;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "test_person")
@Data
public class PersonEntity {

    private Long id;

    private String name;

    private Long birth;

    private int status;

}
