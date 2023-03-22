package com.github.nineteen;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;

//@Entity
//@Table(name = "test_person")
public class PersonEntity {

    private Long id;

    private String name;

    private String extInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
