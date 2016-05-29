package cz.muni.fi.pv242.service;

import java.util.List;

import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;

/**
 * Created by honza on 5/18/16.
 */
public interface UserService {

    UserDTO createUser(UserCreateDTO user);
    UserDTO updateUser(UserUpdateDTO user);
    UserDTO getUserById(long id);
    UserDTO getUserByEmail(String email);
    void enableUser(long u);
    void disableUser(long u);
    boolean authenticate(long userId, String password);
    List<UserDTO> getAllUsers();
}
