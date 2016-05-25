package cz.muni.fi.pv242.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.StringTokenizer;

import cz.muni.fi.pv242.persistence.entity.UserRole;
import cz.muni.fi.pv242.rest.model.User;

@Named
public class JobItemProcessor implements ItemProcessor {


	@Override
	public User processItem(Object t) {
		System.out.println("processItem: " + t);

		StringTokenizer tokens = new StringTokenizer((String)t, ",");
		User u = new User();
		u.setName(tokens.nextToken());
		u.setSurname(tokens.nextToken());
		u.setEmail(tokens.nextToken());
		u.setAge(Integer.valueOf(tokens.nextToken()));
		u.setEnabled(true);
		u.setPassword(tokens.nextToken());

		u.setRoles(new ArrayList<UserRole>());
		u.getRoles().add(UserRole.ADMIN);
		return u;
	}

}
