package cz.muni.fi.pv242.rest;

import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.rest.model.DateModel;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/books")
public interface RestBookService {
	
	@GET
	@Path("/")
	List<BookDTO> getAllBooks();

	@POST
    @Path("/add")
    BookDTO addBook(BookCreateDTO book);

    @GET
    @Path("/{id}")
    BookDTO getBook(@PathParam("id") long id);

    @PUT
    @Path("/update")
    BookDTO updateBook(BookDTO updatedBook);

    @DELETE
    @Path("/{id}/delete")
    boolean deleteBook(@PathParam("id") long id);
    
    @GET
    @Path("/{id}/available")
    DateModel whenIsBookAvailable(@PathParam("id") long id);
    
    @GET
    @Path("/{id}/isavailable")
    boolean isBookAvailable(@PathParam("id") long id);
    
    @GET
    @Path("/{id}/canbereserved")
    boolean canBookBeReserved(@PathParam("id") long id);
}
