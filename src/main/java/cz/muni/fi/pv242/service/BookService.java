package cz.muni.fi.pv242.service;

import java.util.List;

import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;

/**
 * Created by honza on 5/18/16.
 */
public interface BookService {
    BookDTO createBook(BookCreateDTO b);
    BookDTO updateBook(BookDTO b);
    void deleteBook(BookDTO b);
    BookDTO getBookByID(long id);
    List<BookDTO> getAllBooks();
}
