package cz.muni.fi.pv242.rest.model;

import cz.muni.fi.pv242.persistence.entity.Book;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Data
@XmlRootElement()
public class ReservationDTO {

    @NotNull
    private Long id;

    @NotNull
    private Book reservedBook;

    @NotNull
    private java.util.Calendar reservationDate;

    @NotNull
    private java.util.Calendar borrowingDate;
}
