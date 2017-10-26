package net.dev.jcd.uts;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class MyServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    public MyServer(Integer port, String host) {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
        server.start(serverBuilder);
    }

    public DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass(applicationClass.getName());
        return server.undertowDeployment(deployment, appPath);
    }

    public void deploy(DeploymentInfo deploymentInfo) throws ServletException {
        server.deploy(deploymentInfo);
    }

    public static void main(String[] args) throws ServletException {
        MyServer myServer = new MyServer(8080, "0.0.0.0");
        
        DeploymentInfo di = myServer.deployApplication("/rest", UtsApplication.class)
                .setClassLoader(MyServer.class.getClassLoader())
                .setContextPath("/myApp")
                .setDeploymentName("UTS Demo")
                .addServlets(Servlets.servlet("helloServlet", net.dev.jcd.uts.UtsServlet.class).addMapping("/hello"))
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        myServer.deploy(di);
    }
}