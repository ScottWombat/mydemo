package org.demo.dao;


import java.util.List;

import jakarta.persistence.EntityManager;



public interface GenericDao<K, E> {

    List<E> findAll();

    E getByField(String field, String value);

    E getById(K key);

    EntityManager getEntityManager();

}