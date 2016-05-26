package cz.muni.fi.pv242.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class JMSService {

	@Resource(mappedName = "java:jboss/exported/jms/queue/test")
	private Queue queue;

	@Inject
	JMSContext context;

	public void sendMessage(String txt) {

		try {

			context.createProducer().send(queue, txt);

		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

	}
}
