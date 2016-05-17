package cz.muni.fi.pv242.persistence;



import cz.muni.fi.pv242.persistence.entity.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by michal on 5/14/16.
 */
@Stateful
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public User getById(Long id){
        return em.find(User.class, id);
    }

    public User getByEmail(String email){
        return em.find(User.class, email);
    }

    public List<User> getAll(){
        return em.createQuery("SELECT b FROM User b").getResultList();
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) {
        em.remove(user);
    }



}
