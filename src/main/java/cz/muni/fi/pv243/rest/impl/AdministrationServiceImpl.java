package cz.muni.fi.pv243.rest.impl;

import javax.ejb.EJB;

import java.util.List;

import cz.muni.fi.pv243.api.UserService;
import cz.muni.fi.pv243.rest.AdministrationService;
import cz.muni.fi.pv243.rest.model.User;

public class AdministrationServiceImpl implements AdministrationService {
	@EJB
	UserService service;

	public User addUser(User user) {
		return service.addUser(user);
	}

	public User getUser(long id) {
		return service.getUser(id);
	}

	public void disableUser(long id) {
		service.desableUser(id);
	}

	public void enableUser(long id) {
		service.enableUser(id);
	}

	public User updateUser(User updatedUser) {
		return service.updateUser(updatedUser);
	}

	public List<User> findUser(String name, String surname, String email) {

		return service.findUser(name,surname,email);
	}

	public String test()
	{
		return "OK";
	}
}
