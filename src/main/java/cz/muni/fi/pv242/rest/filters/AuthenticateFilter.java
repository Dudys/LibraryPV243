package cz.muni.fi.pv242.rest.filters;

import cz.muni.fi.pv242.service.UserService;

import org.jboss.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

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
        final long userId = userService.getUserByEmail(username).getId();

        if(!userService.authenticate(userId, password)){
            Response response = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Incorrect combination of username and password.")
                    .build();
            requestContext.abortWith(response);
            return;
        }

		System.out.println("filter");
	}
}
