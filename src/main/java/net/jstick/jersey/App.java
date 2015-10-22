package net.jstick.jersey;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class App 
{
    public static void main( String[] args ) {
    	
        try {
            final ResourceConfig rc = new ResourceConfig().packages("net.jstick.jersey");
            final URI bind = new URI("http://0.0.0.0:9090/api/");
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(bind, rc,false);
            server.getServerConfiguration().setDefaultErrorPageGenerator(new CustomErrorPageGenerator());
            CLStaticHttpHandler staticHandler = new CLStaticHttpHandler(App.class.getClassLoader(),"static/");
            server.getServerConfiguration().addHttpHandler(staticHandler, "/*");
            
              server.start();
              System.out.println(String.format("Jersey app started"));
              System.in.read();
              server.shutdown();
              System.out.println("Jersey app stopped");
          } catch (Exception e){
        	  e.printStackTrace();
          }
        
    }
}