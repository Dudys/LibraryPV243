package cz.muni.fi.pv242.rest.model;

import cz.muni.fi.pv242.persistence.entity.Borrowing;
import lombok.Data;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Data
@XmlRootElement()
public class BookCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Pattern(regexp = "((978[\\--– ])?[0-9][0-9\\--– ]{10}[\\--– ][0-9xX])|((978)?[0-9]{9}[0-9Xx])")
    private String isbn;

    @NotNull
    @Min(1L)
    private Integer totalItems;

    @Null
    private List<Borrowing> borrowings = null;
}
