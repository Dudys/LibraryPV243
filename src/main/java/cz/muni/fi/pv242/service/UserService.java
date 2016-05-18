package cz.muni.fi.pv242.service;

import cz.muni.fi.pv242.rest.model.User;

/**
 * Created by honza on 5/18/16.
 */
public interface UserService {

    User createUser(User user);
    User updateUser(User user);
    User getUserById(long id);
    User getUserByEmail(String email);
    void enableUser(long u);
    void disableUser(long u);
    boolean authenticate(long userId, String password);
}
