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

    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    public Reservation getById(Long id){
    	try{
    		return em.find(Reservation.class, id);
    	} catch (Exception e){
    		return null;
    	}
    }

    public List<Reservation> getAll(){
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    public void delete(Reservation reservation) {
        em.remove(em.merge(reservation));
    }

    public Reservation getByBook(long bookId){
    	try {
    		return em.createQuery("SELECT r FROM Reservation r WHERE r.reservedBook.id = :bookId", Reservation.class)
    				.setParameter("bookId", bookId).getSingleResult();
    	} catch (Exception e){
    		return null;
    	}
    }

}
