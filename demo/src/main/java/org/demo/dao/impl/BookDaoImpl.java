package org.demo.dao.impl;

import org.demo.model.Book;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Stateless
public class BookDaoImpl extends GenericDaoImpl<Integer, Book> {
    @PersistenceContext(unitName = "persistence-unit")
    public EntityManager em;

    @Override
    public EntityManager getEntityManager() {
	return em;
    }
}