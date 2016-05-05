package cz.muni.fi.pv243.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

import cz.muni.fi.pv243.rest.model.Borrowing;
import cz.muni.fi.pv243.rest.model.Reservation;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/user/{id}")
public interface UserService {

	@POST
	@Path("/borrow")
	void borrowBook(@PathParam("id")long userId, Borrowing borrowing);

	/**
	 *
	 * @param userId
	 * @param borrowing only required field is id
	 */
	@POST
	@Path("/return")
	void returnBook(@PathParam("id")long userId, Borrowing borrowing);


	@POST
	@Path("/reserve")
	void reserveBook(@PathParam("id")long userId, Reservation reservation);

	/**
	 *
	 * @param userId
	 * @param reservation only required field is id
	 */
	@POST
	@Path("/unreserve")
	void unreserveBook(@PathParam("id")long userId, Reservation reservation);

	@GET
	@Path("/allReservations")
	List<Reservation> getAllReservations(@PathParam("id")long userId);

	@GET
	@Path("/history")
	List<Borrowing> getHistory(@PathParam("id")long userId);
}
