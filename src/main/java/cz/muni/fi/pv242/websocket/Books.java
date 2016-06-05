package cz.muni.fi.pv242.websocket;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import cz.muni.fi.pv242.service.BookService;
import lombok.extern.java.Log;

@Log
@ServerEndpoint("/books")
public class Books {

	@Inject
	private BookService bookService;

	@OnMessage
	public String onMessage(String message)
	{
		log.info("get message " + message);
		if(!message.contains(":"))
		{
			return "operation is not supported";
		}
		String[] tokens = message.split(":");
		if("getBook".equals(tokens[0]))
		{
			try {
				Long id = Long.parseLong(tokens[1]);
				return String.valueOf(bookService.getBookByID(id));
			}catch (NumberFormatException e)
			{
				return "wrong book id format";
			}
		}
		return "operation is not supported";
	}


	@OnOpen
	public void myOnOpen(Session session) {
		log.info("WebSocket opened: " + session.getId());
	}

	@OnClose
	public void myOnClose(CloseReason reason) {
		log.info("Closing a WebSocket due to " + reason.getReasonPhrase());
	}
}
