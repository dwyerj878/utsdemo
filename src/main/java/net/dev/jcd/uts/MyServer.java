package net.dev.jcd.uts;

import io.undertow.Undertow;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 * 
 * Create and start Undertow with CDI
 * 
 * @author jcdwyer@jcd-dev.net
 *
 */
public class MyServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    public MyServer(Integer port, String host) {
        Undertow.Builder serverBuilder = Undertow.builder()
        		.addHttpListener(port, host);
        		//.setHandler(HttpHandlerandler);
        server.start(serverBuilder);
    }

    public DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");        
        deployment.setApplicationClass(applicationClass.getName());
        deployment.setSecurityEnabled(isSecurityEnabled());
        
        return server.undertowDeployment(deployment, appPath);
    }

    public void deploy(DeploymentInfo deploymentInfo) throws ServletException {
        server.deploy(deploymentInfo);
    }

    public static void main(String[] args) throws ServletException {
        MyServer myServer = new MyServer(8080, "0.0.0.0");
        
        AuthenticationMechanism auth = new BasicAuthenticationMechanism("local");
        
        
		DeploymentInfo di = myServer.deployApplication("/rest", UtsApplication.class)
                .setClassLoader(MyServer.class.getClassLoader())
                .setContextPath("/myApp")
                .setDeploymentName("UTS Demo")
                .addServlets(Servlets.servlet("helloServlet", net.dev.jcd.uts.UtsServlet.class).addMapping("/hello"))
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        myServer.deploy(di);
    }
    
    private boolean isSecurityEnabled() {
    	return ConfigResolver.resolve("security.enabled").as(Boolean.class).withDefault(Boolean.TRUE).getValue();
    }
}