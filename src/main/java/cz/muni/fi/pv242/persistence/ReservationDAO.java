package cz.muni.fi.pv242.persistence;


import cz.muni.fi.pv242.persistence.entity.Reservation;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by michal on 5/14/16.
 */
@Stateful
public class ReservationDAO {

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public void create(Reservation book) {
        em.persist(book);
    }

    public Reservation getById(Long id){
        return em.find(Reservation.class, id);
    }

    public List<Reservation> getAll(){
        return em.createQuery("SELECT b FROM Reservation b").getResultList();
    }

    public void update(Reservation book) {
        em.merge(book);
    }

    public void delete(Reservation book) {
        em.remove(book);
    }



}
