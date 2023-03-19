package com.github.nineteen;

@SmartDAO(datasource = "person")
public interface PersonDAO {

    PersonEntity getById(Long id);

}
