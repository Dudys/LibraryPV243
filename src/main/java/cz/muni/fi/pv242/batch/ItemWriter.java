package cz.muni.fi.pv242.batch;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

import cz.muni.fi.pv242.rest.model.User;
import cz.muni.fi.pv242.service.UserService;

@Named(value = "ItemWriter")
public class ItemWriter extends AbstractItemWriter {

	@Inject
	private UserService userService;

	@Override
	public void writeItems(List list) {
		System.out.println("writeItems: " + list);
		for (Object user : list) {
			userService.createUser((User)user);
		}
	}
}
