package com.github.nineteen;

import com.github.nineteen.smartorm.SmartDAO;

import java.util.List;

@SmartDAO(datasource = "datasource", entity = PersonEntity.class)
public interface PersonDAO {

    int insert(PersonEntity entity);

    PersonEntity getById(Long id);

    int deleteById(Long id);
    List<PersonEntity> findByGreaterThanBirth(Long birth);
}
