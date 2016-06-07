package cz.muni.fi.pv242.rest.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import cz.muni.fi.pv242.persistence.entity.Book;

/**
 * Created by honza on 5/18/16.
 */
@Data
@XmlRootElement()
public class BorrowingCreateDTO {

    @NotNull
    private java.util.Calendar startDate;

    @NotNull
    private java.util.Calendar endDate;
}
