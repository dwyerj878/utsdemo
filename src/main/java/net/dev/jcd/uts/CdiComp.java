package net.dev.jcd.uts;

import javax.enterprise.inject.Produces;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.qpid.client.PooledConnectionFactory;
import org.apache.qpid.url.URLSyntaxException;

public class CdiComp {
//	@PropertyInject("jms.maxConnections")
//	int maxConnections;

	@Produces
//	@Named("cfx")
//	@ApplicationScoped
	AMQPComponent cfx() {
		String cfs = "amqp://guest:guest@client/dev?brokerlist='tcp://localhost:5672'";
		
		PooledConnectionFactory cx = new PooledConnectionFactory();
		try {
			cx.setConnectionURLString(cfs);
		} catch (URLSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String cs = "amqp://localhost:5672/dev";
//		JmsConnectionFactory cx = new JmsConnectionFactory("guest", "guest", cs);
		AMQPComponent component = new AMQPComponent(cx);		
		return component;
	}
	
//	@Produces
//	AMQPConnectionDetails amqpConnection() {
//		
//	  AMQPConnectionDetails d = new AMQPConnectionDetails("amqp://localhost:5672/dev","guest", "guest");
//	return d;
//	  
//
//	}
	
//	 
}