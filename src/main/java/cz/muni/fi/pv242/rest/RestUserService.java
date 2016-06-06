package cz.muni.fi.pv242.rest;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import cz.muni.fi.pv242.rest.filters.Authenticate;
import cz.muni.fi.pv242.rest.model.*;

/**
 * Created by honza on 5/18/16.
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public interface RestUserService {

    @Authenticate
    @POST
    @Path("/add")
    UserDTO addUser(UserCreateDTO user);

    @POST
    @Path("/auth")
    UserDTO authUser(UserAuthenticateDTO user);

    @GET
    @Path("/{id}")
    UserDTO getUser(@PathParam("id") long id);

    @PUT
    @Path("/{id}/disable")
    void disableUser(@PathParam("id") long id);

    @PUT
    @Path("/{id}/enable")
    void enableUser(@PathParam("id") long id);

    @PUT
    @Path("/update")
    UserDTO updateUser(UserUpdateDTO updatedUser);

    @GET
    @Path("/findByEmail")
    UserDTO findUserByEmail(@QueryParam("email") String email);

    @GET
    @Path("/createDataBatchJob")
    String runJob();

    @GET
    @Path("/")
    List<UserDTO> getAllUsers();

    @GET
    @Path("/{id}/reservations")
    List<ReservationDTO> getReservationsOfUser(@PathParam("id") long id);

    @GET
    @Path("/{id}/borrowings")
    List<BorrowingDTO> getBorrowingsOfUser(@PathParam("id") long id);

    @POST
    @Path("/{id}/add/reservation")
    ReservationDTO addReservationToUser(@PathParam("id") long id, ReservationCreateDTO reservation);

    @POST
    @Path("/{id}/add/borrowing/{bookId}")
    BorrowingDTO addBorrowingToUser(@PathParam("id") long id, BorrowingCreateDTO borrowing, @PathParam("bookId") long bookId);

    @DELETE
    @Path("/{id}/delete/reservation/{reservationId}")
    void removeReservationOfUser(@PathParam("id") long id, @PathParam("reservationId") long reservationId);

    @DELETE
    @Path("/{id}/delete/borrowing/{borrowingId}")
    void removeBorrowingOfUser(@PathParam("id") long id, @PathParam("borrowingId") long borrowingId);
}
