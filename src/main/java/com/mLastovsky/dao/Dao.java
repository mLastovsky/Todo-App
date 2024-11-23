package com.mLastovsky.dao;

import com.mLastovsky.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E extends Entity> {

    boolean delete(K id);

    E save(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
