package cz.muni.fi.pv242.rest.model;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import cz.muni.fi.pv242.persistence.entity.UserRole;
import lombok.Data;

/**
 * Created by Jan Duda on 5/28/16.
 */

@Data
@XmlRootElement()
public class UserCreateDTO {
	
	@NotNull
	@Size(min=2, max=50)
    private String name;
	
	@NotNull
	@Size(min=2, max=50)
    private String surname;
	
	@NotNull
    @Pattern(regexp = ".+@.+\\....?")
    private String email;
	
	@NotNull
    private List<UserRole> roles;
	
	@NotNull
	@Min(1)
    private int age;
	
	@NotNull
    private boolean enabled;
	
	@NotNull
	@Size(min=8)
    private String password;
}
