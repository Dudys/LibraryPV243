package cz.muni.fi.pv242.rest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import cz.muni.fi.pv242.persistence.entity.Borrowing;
import lombok.Data;

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

    @Override
    public String toString()
    {
        return "Book id=" + id + ", name=" + name + ", totalItems=" + totalItems;
    }
}
