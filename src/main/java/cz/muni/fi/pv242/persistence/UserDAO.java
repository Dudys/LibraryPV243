package cz.muni.fi.pv242.persistence;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import java.util.List;

import cz.muni.fi.pv242.persistence.entity.User;

/**
 * Created by michal on 5/14/16.
 */

@Stateful
public class UserDAO {

    @PersistenceContext(unitName = "persistenceUnit", type= PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public User getById(Long id){
        return em.find(User.class, id);
    }

    public User getByEmail(String email){
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email",email).getSingleResult();
    }

    public List<User> getAll(){
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) {
        em.remove(em.merge(user));
    }

}
