package cz.muni.fi.pv242.rest.model;

import cz.muni.fi.pv242.persistence.entity.Borrowing;
import lombok.Data;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by honza on 5/18/16.
 */
@Data
@XmlRootElement()
public class BookDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Pattern(regexp = "(978-)?[0-9]{1,5}-?[0-9]{1,7}-?[0-9]{1,6}-?[0-9]{1}")
    private String isbn;

    @NotNull
    @Min(1L)
    private Integer totalItems;

    private List<Borrowing> borrowings;
}
