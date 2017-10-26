package net.dev.jcd.uts;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.apache.camel.model.RouteDefinition;
import org.apache.qpid.jms.JmsConnectionFactory;

import com.sun.istack.logging.Logger;

public class SimpleRoute extends RouteBuilder {
	private static final Logger LOG = Logger.getLogger(SimpleRoute.class);

    @Override
    public void configure() {
    	LOG.info("create Route IN");
        from("amqp:queue:test.in")
        .log(LoggingLevel.DEBUG, "Start Simple Route")
        .process(new SimpleProcessor())
        .log(LoggingLevel.DEBUG, "Finish Simple Route")
        .to("amqp:test.out");
    }
    
}
