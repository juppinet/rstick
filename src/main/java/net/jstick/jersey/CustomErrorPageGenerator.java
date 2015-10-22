package net.jstick.jersey;

import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;

public class CustomErrorPageGenerator implements ErrorPageGenerator {

	
    @Override
    public String generate(final Request request,
       final int status, final String reasonPhrase,
       final String description, final Throwable exception) {
    	return CustomHtmlHelper.getErrorPageDetailed(status, reasonPhrase, description, request.getServerName());
    }
}
