package cz.muni.fi.pv242.rest.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv242.persistence.entity.UserRole;
import cz.muni.fi.pv242.rest.model.User;
import cz.muni.fi.pv242.service.UserService;

/**
 * Created by honza on 5/18/16.
 */
@Stateless
public class RestUserServiceImpl implements cz.muni.fi.pv242.rest.UserService {

    @Inject
    UserService userService;

    @Override
    public User addUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User getUser(long id) {
        return userService.getUserById(id);
    }

    @Override
    public void disableUser(long id) {
        userService.disableUser(id);

    }

    @Override
    public void enableUser(long id) {
        userService.enableUser(id);
    }

    @Override
    public User updateUser(User updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @Override
    public User findUserByEmail(String email) {

        User u = new User();
        u.setEnabled(true);
        u.setSurname("Doe");
        u.setName("John ");
        u.setEmail("john@doe.cz");
        u.setAge(15);
        u.setPassword("password");
        List<UserRole> l = new ArrayList<>();
        l.add(UserRole.CUSTOMER);
        u.setRoles(l);

        return u;



                //service.getUserByEmail(email);
    }

    @Override
    public String test() {
        return "{'ok' : 'ok'}";
    }


}
