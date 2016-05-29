package cz.muni.fi.pv242.rest.model;

import lombok.Data;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

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
    @Pattern(regexp = "(978-)?[0-9]{1,5}-?[0-9]{1,7}-?[0-9]{1,6}-?[0-9]{1}")
    private String isbn;

    @NotNull
    @Min(1)
    private Integer totalItems;
}
