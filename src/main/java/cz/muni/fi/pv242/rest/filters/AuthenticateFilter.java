package cz.muni.fi.pv242.rest.filters;

import org.jboss.util.Base64;

import javax.inject.Inject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import cz.muni.fi.pv242.service.UserService;

@Provider
@Authenticate
public class AuthenticateFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@Inject
	UserService userService;

	@Override public void filter(ContainerRequestContext requestContext) throws IOException {

		//Get request headers
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if(authorization == null || authorization.isEmpty())
		{

			Response response = Response
					.status(Response.Status.UNAUTHORIZED)
					.type(MediaType.TEXT_PLAIN_TYPE)
					.entity("Access denied for this resource.")
					.build();
			requestContext.abortWith(response);
			return;
		}

		//Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		//Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword));

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// TODO: Login Context might be connected to HTTPBasic auth somehow here, if required (?)

		// Obtain a LoginContext, needed for authentication. Tell it
		// to use the LoginModule implementation specified by the
		// entry named "Sample" in the JAAS login configuration
		// file and to also use the specified CallbackHandler.
		LoginContext lc = null;

		try {
			lc = new LoginContext("Sample", new LocalCallbackHandler());

			// attempt authentication
			lc.login();

		} catch (LoginException | SecurityException le) {
			System.err.println("Cannot create LoginContext." + le.getMessage());
			System.err.println("Auth failed for " + lc.getSubject().getPrivateCredentials());
			System.exit(-1);
		}
	}
}

