package cz.muni.fi.pv242.rest;

import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/borrowing")
public interface RestBorrowingService {

    @POST
    @Path("/add")
    BorrowingDTO addBook(BorrowingCreateDTO borrowing);

    @GET
    @Path("/{id}")
    BorrowingDTO getBorrowing(@PathParam("id") long id);

    @PUT
    @Path("/update")
    BorrowingDTO updateBook(BorrowingDTO updatedBorrowing);

    @PUT
    @Path("/{id}/delete")
    void deleteBook(@PathParam("id") long id);
}
