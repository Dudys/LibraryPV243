package cz.muni.fi.pv243.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;

import cz.muni.fi.pv243.rest.model.Book;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/book")
public interface BookService {

	@POST
	@Path("/add")
	Book addBook(Book book);

	@GET
	@Path("/{id}")
	Book getBook(@PathParam("id") long id);

	@DELETE
	@Path("/{id}") void removeBook(@PathParam("id") long id);

	@PUT
	@Path("/{id}")
	Book updateBook(@PathParam("id") long id, Book updatedBook);

	@GET
	@Path("/find")
	List<Book> findBook(@QueryParam("name") String name, @QueryParam("author") String author);



}
