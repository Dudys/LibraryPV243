package cz.muni.fi.pv242.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cz.muni.fi.pv242.rest.filters.Authenticate;
import cz.muni.fi.pv242.rest.model.UserCreateDTO;
import cz.muni.fi.pv242.rest.model.UserDTO;
import cz.muni.fi.pv242.rest.model.UserUpdateDTO;

/**
 * Created by honza on 5/18/16.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public interface UserService {

    @Authenticate
    @POST
    @Path("/add")
    UserDTO addUser(UserCreateDTO user);

    @GET
    @Path("/{id}")
    UserDTO getUser(@PathParam("id") long id);

    @PUT
    @Path("/{id}/disable")
    void disableUser(@PathParam("id") long id);

    @PUT
    @Path("/{id}/disable")
    void enableUser(@PathParam("id") long id);

    @PUT
    @Path("/update") UserDTO updateUser(UserUpdateDTO updatedUser);

    @GET
    @Path("/findByEmail")
    UserDTO findUserByEmail(@QueryParam("email") String email);

    @GET
    @Path("/createDataBatchJob")
    String runJob();

    @GET
    @Path("/")
    List<UserDTO> getAllUsers();
}
