package cz.muni.fi.pv243.rest.impl;

import javax.ejb.EJB;

import java.util.List;

import cz.muni.fi.pv243.rest.BookService;
import cz.muni.fi.pv243.rest.model.Book;

public class BookServiceImpl implements BookService {

	@EJB
	private cz.muni.fi.pv243.api.BookService service;

	public Book addBook(Book book) {
		return service.addBook(book);
	}

	public Book getBook(long id) {
		return service.getBook(id);
	}

	public void removeBook(long id) {
		service.removeBook(id);
	}

	public Book updateBook(long id, Book updatedBook) {
		return service.updateBook(id, updatedBook);
	}

	public List<Book> findBook(String name, String author) {
		return service.findBook(name, author);
	}
}
