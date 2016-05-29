package cz.muni.fi.pv242.rest.impl;

import cz.muni.fi.pv242.rest.RestBookService;
import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.service.BookService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public class RestBookServiceImpl implements RestBookService {

    @Inject
    BookService bookService;
    
    @Override
    public List<BookDTO> getAllBooks(){
    	return bookService.getAllBooks();
    }

    @Override
    public BookDTO addBook(BookCreateDTO book){
        return bookService.createBook(book);
    }

    @Override
    public BookDTO getBook(long id) {
    	return bookService.getBookByID(id);
    }

    @Override
    public BookDTO updateBook(BookDTO updatedBook) {
        return bookService.updateBook(updatedBook);
    }

    @Override
    public void deleteBook(long id) {
        bookService.deleteBook(bookService.getBookByID(id));
    }
}
