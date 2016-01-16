package net.jstick.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig{
	
	public AppConfig(){
		packages("net.jstick.jersey");
        register(JacksonFeature.class);
	}

}
