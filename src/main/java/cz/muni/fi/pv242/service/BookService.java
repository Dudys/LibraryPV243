package cz.muni.fi.pv242.service;

import cz.muni.fi.pv242.rest.model.Book;

/**
 * Created by honza on 5/18/16.
 */
public interface BookService {
    Book createBook(Book b);
    Book updateBook(Book b);
    Book deleteBook(Book b);
    Book getBookByID(long id);
    Book searchBook(Book b);

}
