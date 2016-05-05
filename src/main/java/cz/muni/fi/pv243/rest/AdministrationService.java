package cz.muni.fi.pv243.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;

import cz.muni.fi.pv243.rest.model.User;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/admin")
public interface AdministrationService {

	@POST
	@Path("/user/add") User addUser(User user);

	@GET
	@Path("/user/{id}")
	User getUser(@PathParam("id") long id);

	@PUT
	@Path("/user/{id}/disable")
	void disableUser(@PathParam("id") long id);

	@PUT
	@Path("/user/{id}/disable")
	void enableUser(@PathParam("id") long id);

	@PUT
	@Path("/user") User updateUser(User updatedUser);

	@GET
	@Path("/user/find") List<User> findUser(@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("email") String email);

	@GET
	@Produces("text/plain")
	@Path("/test")
	public String test();
}
