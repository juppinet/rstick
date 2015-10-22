package net.jstick.jersey;

import java.io.IOException;
import java.io.Writer;

import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

public class CustomHtmlHelper {

	private final static String CSS2 =
    		"html { padding: 30px 10px;font-size: 20px;line-height: 1.4;color: #737373;background: #f0f0f0;font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;-webkit-text-size-adjust: 100%;-ms-text-size-adjust: 100%;}\n" +
    		"body {height: 200px;max-width: 550px;_width: 550px;padding: 30px 20px 50px;border: 1px solid #b3b3b3;border-radius: 4px;margin: 0 auto;box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;background: #fcfcfc;}\n" +
    		"h1 {margin: 0 10px;font-size: 50px;text-align: center;}\n" +
    		"h1 span {color: #bbb;}h3 {margin: 1.5em 0 0.5em;}p {margin: 1em 0;}ul {padding: 0 0 0 40px;margin: 1em 0;}\n" +
    		".container { max-width: 500px; _width: 500px;margin: 0 auto;}\n";

    /**
     * Generate and send an error page for the given HTTP response status.
     * Unlike {@link #setErrorAndSendErrorPage(org.glassfish.grizzly.http.server.Request, org.glassfish.grizzly.http.server.Response, org.glassfish.grizzly.http.server.ErrorPageGenerator, int, java.lang.String, java.lang.String, java.lang.Throwable)},
     * this method doesn't change the {@link Response} status code and reason phrase.
     * 
     * @param request
     * @param response
     * @param generator
     * @param status
     * @param reasonPhrase
     * @param description
     * @param exception
     * 
     * @throws IOException 
     */
    public static void sendErrorPage(
            final Request request, final Response response,
            final ErrorPageGenerator generator,
            final int status, final String reasonPhrase,
            final String description, final Throwable exception) throws IOException {
        
        if (generator != null && !response.isCommitted() &&
                response.getOutputBuffer().getBufferedDataSize() == 0) {
            final String errorPage = generator.generate(request, status,
                    reasonPhrase, description, exception);
            
            final Writer writer = response.getWriter();
            
            if (errorPage != null) {
                if (!response.getResponse().isContentTypeSet()) {
                    response.setContentType("text/html");
                }
                
                writer.write(errorPage);
            }
            writer.close();
        }
    }
    
    /**
     * Generate and send an error page for the given HTTP response status.
     * Unlike {@link #setErrorAndSendErrorPage(org.glassfish.grizzly.http.server.Request, org.glassfish.grizzly.http.server.Response, org.glassfish.grizzly.http.server.ErrorPageGenerator, int, java.lang.String, java.lang.String, java.lang.Throwable)},
     * this method does change the {@link Response} status code and reason phrase.
     * 
     * @param request
     * @param response
     * @param generator
     * @param status
     * @param reasonPhrase
     * @param description
     * @param exception
     * 
     * @throws IOException 
     */
    public static void setErrorAndSendErrorPage(
            final Request request, final Response response,
            final ErrorPageGenerator generator,
            final int status, final String reasonPhrase,
            final String description, final Throwable exception) throws IOException {
        
        response.setStatus(status, reasonPhrase);
        
        if (generator != null && !response.isCommitted() &&
                response.getOutputBuffer().getBufferedDataSize() == 0) {
            final String errorPage = generator.generate(request, status,
                    reasonPhrase, description, exception);
            
            final Writer writer = response.getWriter();
            
            if (errorPage != null) {
                if (!response.getResponse().isContentTypeSet()) {
                    response.setContentType("text/html");
                }
                
                writer.write(errorPage);
            }
            writer.close();
        }
    }

    public static void writeTraceMessage(final Request request,
            final Response response) throws IOException {
        response.setStatus(HttpStatus.OK_200);
        response.setContentType("message/http");
        final Writer writer = response.getWriter();
        writer.append(request.getMethod().toString()).append(' ')
                .append(request.getRequest().getRequestURIRef().getOriginalRequestURIBC().toString())
                .append(' ').append(request.getProtocol().getProtocolString())
                .append("\r\n");

        for (String headerName : request.getHeaderNames()) {
            for (String headerValue : request.getHeaders(headerName)) {
                writer.append(headerName).append(": ").append(headerValue).append("\r\n");
            }
        }
    }

    /**
     *
     * @return A {@link String} containing the HTTP response.
     */
    public static String getErrorPage(String headerMessage,
            String message, String serverName) {
        return prepareBody(headerMessage, message, serverName);
    }
    
    public static String getErrorPageDetailed(int status, String headerMessage,
            String message, String serverName) {
        return prepareBody(status,headerMessage, message, serverName);
    }



    /**
     * Prepare the HTTP body containing the error messages.
     */

    private static String prepareBody(int status,String headerMessage, String message,
            String serverName) {
        final StringBuilder sb = new StringBuilder();

        sb.append("<html><head><title>");
        sb.append(serverName);
        sb.append("</title>");
        sb.append("<style>");
        sb.append(CSS2);
        sb.append("</style> ");
        sb.append("</head><body>");
        sb.append("<div class=\"container\">");
        sb.append("<h1>");
        sb.append(status + " ");
        sb.append(headerMessage);
        sb.append("</h1>");
        sb.append("<p>");
        sb.append((message != null) ? message : "Unknown");
        sb.append("</p>");
        sb.append("</div>");
        sb.append("</body></html>");
        return sb.toString();
    }
    
    private static String prepareBody(String headerMessage, String message,
            String serverName) {
        final StringBuilder sb = new StringBuilder();

        sb.append("<html><head><title>");
        sb.append(serverName);
        sb.append("</title>");
        sb.append("<style>");
        sb.append(CSS2);
        sb.append("</style> ");
        sb.append("</head><body>");
        sb.append("<div class=\"container\">");
        sb.append("<h1>");
        sb.append(headerMessage);
        sb.append("</h1>");
        sb.append("<p>");
        sb.append((message != null) ? message : "Unknown");
        sb.append("</p>");
        sb.append("</div>");
        sb.append("</body></html>");
        return sb.toString();
    }


   

}
