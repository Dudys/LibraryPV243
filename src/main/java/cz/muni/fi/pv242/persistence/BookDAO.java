package cz.muni.fi.pv242.persistence;


import cz.muni.fi.pv242.persistence.entity.Book;
import cz.muni.fi.pv242.persistence.entity.Borrowing;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by michal on 5/14/16.
 */
@Stateful
public class BookDAO {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public void create(Book book) {
        em.persist(book);
    }

    public Book getById(Long id){
        return em.find(Book.class, id);
    }

    public List<Book> getAll(){
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
    
    public void update(Book book) {
        em.merge(book);
    }

    public void delete(Book book) {
        em.remove(em.merge(book));
    }

}
