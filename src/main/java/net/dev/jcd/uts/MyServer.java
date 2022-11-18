package net.dev.jcd.uts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import io.undertow.Undertow;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import jakarta.servlet.ServletException;
import jakarta.ws.rs.core.Application;

/**
 * 
 * Create and start Undertow with CDI
 * 
 * @author jcdwyer@jcd-dev.net
 *
 */
public class MyServer {

	private final UndertowJaxrsServer server = new UndertowJaxrsServer();

	

	public MyServer(Integer port, String host, boolean ssl, String pathToPkcs12File, String keyPassword) {
		if (ssl) {
			try {			
				createHttpsServer(port,  host, pathToPkcs12File, keyPassword);
			} catch (GeneralSecurityException | IOException e) {			
				createHttpServer(port, host);
			}
		} else {
			createHttpServer(port, host);
		}
		// .setHandler(HttpHandlerandler);
		
	}

	private void createHttpServer(Integer port, String host) {
		try {
			Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
			server.start(serverBuilder);
			System.out.println(String.format("http://%s:%d/myApp/hello", Inet4Address.getLocalHost().getHostAddress(), port));
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
	}

	private void createHttpsServer(int port, String host, String pathToPkcs12File, String keyPassword)
			throws GeneralSecurityException, IOException {
		SSLContext sslContext = createContext(pathToPkcs12File, keyPassword);
		Undertow.Builder serverBuilder = Undertow.builder().addHttpsListener(port, host, sslContext);
		server.start(serverBuilder);
		try {
			System.out.println(String.format("https://%s:%d", Inet4Address.getLocalHost().getHostAddress(), port));
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
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
		
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption(new Option("d", "debug", false, "Turn on debugging"));
		options.addOption(new Option("p", "port", true, "Set the port"));
		options.addOption(new Option("s", "ssl", false, "Turn on ssl"));
		options.addOption(new Option("b", "bind", true, "bind to address"));
		options.addOption(new Option("k", "keyfile", true, "pcs12/rsa keyfile"));
		options.addOption(new Option("w", "keypassword", true, "keyfile password"));
		options.addOption(new Option("h", "help", false, "show help"));
		
		int port = 8081;
		String bind = "0.0.0.0";
		boolean ssl = false;
		boolean debug = false;
		String keyFile ="keystore.pfx";
		String keyPassword = "myspa55wd";

				
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("MyServer", options);
				return;
			}
			if (cmd.hasOption("d")) debug = true;
			if (cmd.hasOption("s")) ssl = true;
			if (cmd.hasOption("p")) port = Integer.valueOf(cmd.getOptionValue("p"));
			if (cmd.hasOption("b")) bind = cmd.getOptionValue("b");
			if (cmd.hasOption("k")) keyFile = cmd.getOptionValue("k");
			if (cmd.hasOption("w")) keyPassword = cmd.getOptionValue("w");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		MyServer myServer = new MyServer(port, bind, ssl, keyFile, keyPassword);
		
		AuthenticationMechanism auth = new BasicAuthenticationMechanism("local");

		DeploymentInfo di = myServer.deployApplication("/rest", UtsApplication.class)
				.setClassLoader(MyServer.class.getClassLoader()).setContextPath("/myApp").setDeploymentName("UTS Demo")
				.addServlets(Servlets.servlet("helloServlet", net.dev.jcd.uts.UtsServlet.class).addMapping("/hello"))
				.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

		myServer.deploy(di);
	}

	private boolean isSecurityEnabled() {
		return ConfigResolver.resolve("security.enabled").as(Boolean.class).withDefault(Boolean.TRUE).getValue();
	}
	
	private SSLContext createContext(String pathToPkcs12File, String password) throws GeneralSecurityException, IOException {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		InputStream keyInput = new FileInputStream(pathToPkcs12File);

		keyStore.load(keyInput, password.toCharArray());
		keyInput.close();
		keyManagerFactory.init(keyStore, password.toCharArray());
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		return context;
	}
}
