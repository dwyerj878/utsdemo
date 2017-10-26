package net.dev.jcd.uts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.impl.DefaultCamelContext;
@ApplicationScoped
public class CamelConf extends DefaultCamelContext {
 
    @PostConstruct
    void customize() {
        // Set the Camel context name
//        setName("custom");
        
        // Disable JMX
//        disableJMX();
//        AMQPComponent amqp = AMQPComponent.amqpComponent("amqp:///dev?brokerlist='tcp://localhost:5672'");       
//        addComponent("amqp", amqp);
    }
 
    @PreDestroy
    void cleanUp() {
        // ...
    }
}