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

    public void create(Borrowing borrowing) {
        em.persist(borrowing);
    }

    public Borrowing getById(Long id){
        return em.find(Borrowing.class, id);
    }

    public List<Borrowing> getAll(){
        return em.createQuery("SELECT b FROM Borrowing b", Borrowing.class).getResultList();
    }

    public void update(Borrowing borrowing) {
        em.merge(borrowing);
    }

    public void delete(Borrowing borrowing) {
        em.remove(em.merge(borrowing));
    }



}
