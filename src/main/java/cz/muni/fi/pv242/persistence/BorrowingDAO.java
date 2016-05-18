package cz.muni.fi.pv242.persistence;



import cz.muni.fi.pv242.persistence.entity.Borrowing;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by michal on 5/14/16.
 */
@Stateful
public class BorrowingDAO {

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public void create(Borrowing book) {
        em.persist(book);
    }

    public Borrowing getById(Long id){
        return em.find(Borrowing.class, id);
    }

    public List<Borrowing> getAll(){
        return em.createQuery("SELECT b FROM Borrowing b", Borrowing.class).getResultList();
    }

    public void update(Borrowing book) {
        em.merge(book);
    }

    public void delete(Borrowing book) {
        em.remove(em.merge(book));
    }



}
