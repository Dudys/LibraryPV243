package cz.muni.fi.pv242;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv242.persistence.entity.UserRole;
import cz.muni.fi.pv242.rest.model.User;
import cz.muni.fi.pv242.service.UserService;

@RunWith(Arquillian.class)
public class JMSTest {
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "Library.war")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/classes/META-INF/persistence.xml"), "classes/META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
				.addPackages(true,"cz.muni.fi.pv242");
	}

	@Resource(mappedName = "java:jboss/exported/jms/queue/test")
	private Queue queue;

	@Inject
	private JMSContext context;

	@Inject
	private UserService service;

	private User user;

	@Before
	public void setUp()
	{
		user = new User();
		user.setAge(50);
		user.setEmail("mail@gmail.xom");
		List<UserRole> l = new ArrayList<>();
		l.add(UserRole.CUSTOMER);
		user.setRoles(l);
		user.setName("John");
		user.setSurname("Doe");
		user.setPassword("myPassword");
		user.setEnabled(true);


	}

	@Test
	public void jmsTest()
	{
		User created = service.createUser(user);
		String str = context.createConsumer(queue).receiveBody(String.class, 5000L);
		Assert.assertFalse(str.isEmpty());
		Assert.assertEquals("Created new User: " + created, str);
	}
}
