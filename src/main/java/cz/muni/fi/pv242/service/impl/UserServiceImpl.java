package cz.muni.fi.pv242.service.impl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv242.jms.JMSService;
import cz.muni.fi.pv242.persistence.UserDAO;
import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;
import cz.muni.fi.pv242.service.UserService;
import cz.muni.fi.pv242.persistence.entity.User;

/**
 * Created by honza on 5/18/16.
 */
@Stateless
public class UserServiceImpl implements UserService{

    @Inject
    UserDAO userDao;

    @Inject
    JMSService jmsService;

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public UserDTO createUser(UserCreateDTO user) {
        User usr = mapper.map(user, User.class);
        usr.setPasswordHash(hashPassword(user.getPassword()));
        userDao.create(usr);
        UserDTO newUser =  mapper.map(usr,UserDTO.class);

        //send message to JMS
        jmsService.sendMessage("Created new User: " + newUser);

        return newUser;
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO user) {
        User usr = mapper.map(user, cz.muni.fi.pv242.persistence.entity.User.class);
        usr.setPasswordHash(hashPassword(user.getPassword()));
        userDao.update(usr);
        return mapper.map(usr, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(long id) {
    	User usr = userDao.getById(id);
    	return mapper.map(usr, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User usr = userDao.getByEmail(email);
        return mapper.map(usr, UserDTO.class);
    }

    @Override
    public void enableUser(long id) {
    	User usr = userDao.getById(id);
    	usr.setEnabled(true);
    	userDao.update(usr);
    }

    @Override
    public void disableUser(long id) {
        User usr = userDao.getById(id);
        usr.setEnabled(false);
        userDao.update(usr);
    }

    @Override
    public boolean authenticate(long userId, String password) {
        User usr = userDao.getById(userId);
        return usr.getPasswordHash().equals(hashPassword(password));
    }

    private String hashPassword(String password)
    {
        String generatedHash = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedHash = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedHash;
    }

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userDao.getAll();
    	List<UserDTO> userDTOs = new ArrayList<>();
    	for (User user : users) {
    		userDTOs.add(mapper.map(user, UserDTO.class));
		}
    	
    	return userDTOs;
	}
}
