/*
 *
 * Copyright (c) 2000, 2002, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

package cz.muni.fi.pv242.rest.filters;

import cz.muni.fi.pv242.service.UserService;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import java.util.Map;

/**
 * <p> This sample LoginModule authenticates users with a password.
 *
 * <p> This LoginModule only recognizes one user:       testUser
 * <p> testUser's password is:  testPassword
 *
 * <p> If testUser successfully authenticates itself,
 * a <code>Principal</code> with the testUser's user name
 * is added to the Subject.
 *
 * <p> This LoginModule recognizes the debug option.
 * If set to true in the login Configuration,
 * debug messages will be output to the output stream, System.out.
 *
 */
public class LoginModule implements javax.security.auth.spi.LoginModule {

	@Inject
	UserService userService;

	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map sharedState;
	private Map options;

	// configurable option
	private boolean debug = false;

	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	// username and password
	private String username;
	private String password;

	// testUser's Principal
	private Principal userPrincipal;

	/**
	 * Initialize this LoginModule.
	 * <p/>
	 * <p> This method is called by the {@code LoginContext}
	 * after this {@code LoginModule} has been instantiated.
	 * The purpose of this method is to initialize this
	 * {@code LoginModule} with the relevant information.
	 * If this {@code LoginModule} does not understand
	 * any of the data stored in {@code sharedState} or
	 * {@code options} parameters, they can be ignored.
	 * <p/>
	 * <p/>
	 *
	 * @param subject         the {@code Subject} to be authenticated. <p>
	 * @param callbackHandler a {@code CallbackHandler} for communicating
	 *                        with the end user (prompting for usernames and
	 *                        passwords, for example). <p>
	 * @param sharedState     state shared with other configured LoginModules. <p>
	 * @param options         options specified in the login
	 *                        {@code Configuration} for this particular
	 *                        {@code LoginModule}.
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {

		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;

		// initialize any configured options
		debug = "true".equalsIgnoreCase((String)options.get("debug"));
	}


	/**
	 * Authenticate the user by prompting for a user name and password.
	 *
	 * <p>
	 *
	 * @return true in all cases since this <code>LoginModule</code>
	 *          should not be ignored.
	 *
	 * @exception javax.security.auth.login.FailedLoginException if the authentication fails. <p>
	 *
	 * @exception javax.security.auth.login.LoginException if this <code>LoginModule</code>
	 *          is unable to perform the authentication.
	 */
	public boolean login() throws LoginException {

		// prompt for a user name and password
		if (callbackHandler == null)
			throw new LoginException("Error: no CallbackHandler available " +
					"to garner authentication information from the user");

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("user name: ");
		callbacks[1] = new PasswordCallback("password: ", false);

		try {
			callbackHandler.handle(callbacks);
			username = ((NameCallback)callbacks[0]).getName();
			char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
			if (tmpPassword == null) {
				// treat a NULL password as an empty password
				tmpPassword = new char[0];
			}
			password = String.valueOf(tmpPassword);
			((PasswordCallback)callbacks[1]).clearPassword();

		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException("Error: " + uce.getCallback().toString() +
					" not available to garner authentication information " +
					"from the user");
		}

		// print debugging information
		if (debug) {
			System.out.println("\t\t[SampleLoginModule] " +
					"user entered user name: " +
					username);
			System.out.print("\t\t[SampleLoginModule] " +
					"user entered password: " + password);

			System.out.println();
		}

		// verify the username/password
		if (userService.authenticate(username, password)) {
			succeeded = true;
			if (debug) {
				System.out.println("\t\t[SampleLoginModule] " +
						"authentication succeeded");
			}
			return true;
		} else {
			succeeded = false;
			// authentication failed -- clean out state
			if (debug) {
				System.out.println("\t\t[SampleLoginModule] " +
						"authentication failed");
			}

			return false;
		}
	}

	/**
	 * <p> This method is called if the LoginContext's
	 * overall authentication succeeded
	 * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
	 * succeeded).
	 *
	 * <p> If this LoginModule's own authentication attempt
	 * succeeded (checked by retrieving the private state saved by the
	 * <code>login</code> method), then this method associates a
	 * <code>Principal</code>
	 * with the <code>Subject</code> located in the
	 * <code>LoginModule</code>.  If this LoginModule's own
	 * authentication attempted failed, then this method removes
	 * any state that was originally saved.
	 *
	 * <p>
	 *
	 * @exception javax.security.auth.login.LoginException if the commit fails.
	 *
	 * @return true if this LoginModule's own login and commit
	 *          attempts succeeded, or false otherwise.
	 */
	public boolean commit() throws LoginException {
		if (!succeeded) {
			return false;
		} else {
			// add a Principal (authenticated identity)
			// to the Subject

			// assume the user we authenticated is the Principal
			userPrincipal = new Principal(username);
			if (!subject.getPrincipals().contains(userPrincipal))
				subject.getPrincipals().add(userPrincipal);

			if (debug) {
				System.out.println("\t\t[SampleLoginModule] " +
						"added Principal to Subject");
			}

			// in any case, clean out state
			username = null;
			password = null;

			commitSucceeded = true;
			return true;
		}
	}

	/**
	 * <p> This method is called if the LoginContext's
	 * overall authentication failed.
	 * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
	 * did not succeed).
	 *
	 * <p> If this LoginModule's own authentication attempt
	 * succeeded (checked by retrieving the private state saved by the
	 * <code>login</code> and <code>commit</code> methods),
	 * then this method cleans up any state that was originally saved.
	 *
	 * <p>
	 *
	 * @exception javax.security.auth.login.LoginException if the abort fails.
	 *
	 * @return false if this LoginModule's own login and/or commit attempts
	 *          failed, and true otherwise.
	 */
	public boolean abort() throws LoginException {
		if (!succeeded) {
			return false;
		} else if (succeeded && commitSucceeded) {
			// login succeeded but overall authentication failed
			succeeded = false;
			username = null;
			if (password != null) {
				password = null;
			}
			userPrincipal = null;
		} else {
			// overall authentication succeeded and commit succeeded,
			// but someone else's commit failed
			logout();
		}
		return true;
	}

	/**
	 * Logout the user.
	 *
	 * <p> This method removes the <code>Principal</code>
	 * that was added by the <code>commit</code> method.
	 *
	 * <p>
	 *
	 * @exception javax.security.auth.login.LoginException if the logout fails.
	 *
	 * @return true in all cases since this <code>LoginModule</code>
	 *          should not be ignored.
	 */
	public boolean logout() throws LoginException {

		subject.getPrincipals().remove(userPrincipal);
		succeeded = false;
		succeeded = commitSucceeded;
		username = null;
		if (password != null) {
			password = null;
		}
		userPrincipal = null;
		return true;
	}
}
