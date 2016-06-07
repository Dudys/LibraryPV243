package cz.muni.fi.pv242.rest.model;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import lombok.Data;

@Data
public class UserAuthenticateDTO {
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
}
