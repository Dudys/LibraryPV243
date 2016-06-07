package cz.muni.fi.pv242.rest.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import cz.muni.fi.pv242.persistence.entity.Book;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Data
@XmlRootElement()
public class BorrowingDTO {

    @NotNull
    private Long id;
    
    private Book borrowedBook;

    @NotNull
    private java.util.Calendar startDate;

    @NotNull
    private java.util.Calendar endDate;
}
