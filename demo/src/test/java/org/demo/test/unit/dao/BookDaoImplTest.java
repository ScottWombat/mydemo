package org.demo.test.unit.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.demo.model.Book;
import org.demo.dao.impl.BookDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@Slf4j
public class BookDaoImplTest {
     private static BookDaoImpl dao;

    @BeforeAll
    public static void before() {
        log.info("BeforeAll");
	    dao = new BookDaoImpl();
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit-test");
	    dao.em = emf.createEntityManager();
    }

    @Test
    public void findAll() throws SQLException {
	    List<Book> lst = dao.findAll();
	    assertThat(lst.size(), equalTo(1));
    
    }
    @Test
    public void getByField() {
	Book obj = dao.getByField("author", "J. K. Rowling");
	assertThat(obj.getId(), equalTo(1));
    }

    @Test
    public void getById() {
	Book obj = dao.getById(1);
	assertThat(obj.getAuthor(), equalTo("J. K. Rowling"));
    }


}
