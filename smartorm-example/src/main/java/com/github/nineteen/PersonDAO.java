package com.github.nineteen;

import com.github.nineteen.smartorm.SmartDAO;

@SmartDAO(datasource = "person")
public interface PersonDAO {

    PersonEntity getById(Long id);

}
