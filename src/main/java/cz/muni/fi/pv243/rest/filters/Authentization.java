package cz.muni.fi.pv243.rest.filters;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.util.Base64;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import cz.muni.fi.pv243.api.UserService;

@Priority(Priorities.AUTHENTICATION)
public class Authentization implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@EJB
	private UserService service;

	public void filter(ContainerRequestContext requestContext) throws IOException {

			//Get request headers
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			//Fetch authorization header
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			//If no authorization information present; block access
			if(authorization == null || authorization.isEmpty())
			{
				requestContext.abortWith(new ServerResponse("Access denied for this resource", 401, new Headers<Object>()));
				return;
			}

			//Get encoded username and password
			final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			//Decode username and password
			String usernameAndPassword = null;
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));

		//Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			//Verifying Username and password
			service.authenticate(username, password);

		}
	}

