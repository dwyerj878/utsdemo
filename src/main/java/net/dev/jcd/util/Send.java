package net.dev.jcd.util;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class Send {
	public Send() {
	}

	public static void main(String[] args) {
		Send producer = new Send();
		producer.runTest();
	}

	private void runTest() {
		try {
			Properties properties = new Properties();
			properties.load(this.getClass().getResourceAsStream("/net/dev/jcd/util/send.properties"));
			Context context = new InitialContext(properties);

			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("qpidConnectionfactory");
			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) context.lookup("queue");

			MessageProducer messageProducer = session.createProducer(destination);
			MessageConsumer messageConsumer = session.createConsumer(destination);
			MapMessage message = session.createMapMessage();
			message.setInt("id", 12345);
			message.setString("name", "Fred");
			messageProducer.send(message);

//			message = (TextMessage) messageConsumer.receive();
//			System.out.println(message.getText());

			connection.close();
			context.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
