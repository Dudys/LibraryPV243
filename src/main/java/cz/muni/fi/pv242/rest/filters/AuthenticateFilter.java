package cz.muni.fi.pv242.rest.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Authenticate
public class AuthenticateFilter implements ContainerRequestFilter {
	@Override public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO @michal here do filter
		System.out.println("filter");
	}
}
