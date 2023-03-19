package com.github.nineteen.smartorm;

import java.io.Serializable;

public interface BaseSmartDAO<T> {

    int insert(T entity);

    int deleteById(Serializable id);

    int updateById(T entity);

    T getById(Serializable id);
}
