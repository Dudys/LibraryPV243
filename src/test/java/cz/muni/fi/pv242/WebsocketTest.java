package cz.muni.fi.pv242;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import cz.muni.fi.pv242.rest.model.BookCreateDTO;
import cz.muni.fi.pv242.rest.model.BookDTO;
import cz.muni.fi.pv242.service.BookService;

@RunWith(Arquillian.class)
public class WebsocketTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "Library.war")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/classes/META-INF/persistence.xml"), "classes/META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
				.addPackages(true,"cz.muni.fi.pv242");
	}


	@Inject
	BookService service;

	private final BookCreateDTO book = new BookCreateDTO();


	@BeforeClass
	public static void setUp() throws InterruptedException {
		//wait for websocket service
		Thread.sleep(5000);
	}
	@Test
	public void websockTest() throws URISyntaxException, InterruptedException {

		book.setName("name");
		book.setTotalItems(2);
		book.setIsbn("afs");
		BookDTO created  = service.createBook(book);



			// open websocket
			final WebsocketClientEndpoint clientTestEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/library-1.0-SNAPSHOT/books"));



			// send message to websocket
			clientTestEndPoint.sendMessage("getBook:" + created.getId());

		Thread.sleep(500);
			// wait 5 seconds for messages from websocket
		Assert.assertNotNull(clientTestEndPoint.getResponseContain());

	}

	@ClientEndpoint
	static class WebsocketClientEndpoint {

		Session userSession = null;
		String responseContain = null;

		public WebsocketClientEndpoint(URI endpointURI) {
			try {
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				container.connectToServer(this, endpointURI);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public String getResponseContain() {
			return responseContain;
		}

		/**
		 * Callback hook for Connection open events.
		 *
		 * @param userSession the userSession which is opened.
		 */
		@OnOpen
		public void onOpen(Session userSession) {
			this.userSession = userSession;
		}

		/**
		 * Callback hook for Connection close events.
		 *
		 * @param userSession the userSession which is getting closed.
		 * @param reason the reason for connection close
		 */
		@OnClose
		public void onClose(Session userSession, CloseReason reason) {
			System.out.println("closing websocket");
			this.userSession = null;
		}

		/**
		 * Callback hook for Message Events. This method will be invoked when a client send a message.
		 *
		 * @param message The text message
		 */
		@OnMessage
		public void onMessage(String message) {
			responseContain = message;
		}



		/**
		 * Send a message.
		 *
		 * @param message
		 */
		public void sendMessage(String message) {
			this.userSession.getAsyncRemote().sendText(message);
		}


	}
	}


