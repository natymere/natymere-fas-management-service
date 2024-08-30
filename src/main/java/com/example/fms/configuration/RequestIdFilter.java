package com.example.fms.configuration;

import com.example.fms.util.RequestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

public class RequestIdFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestIdFilter.class);
    private final ObjectMapper objectMapper;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // Constructor to accept ObjectMapper
    public RequestIdFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Cache the request content for multiple read
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);

        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        // Store request ID and start time in RequestContext
        RequestContext.setRequestId(requestId);
        RequestContext.setRequestStartTime();

        // Get headers
        String httpHeadersStr = getHttpHeadersString(wrappedRequest);

        // Log request details
        if (logger.isInfoEnabled()) {
            logger.info("Starting request processing for {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
            logger.info("Request headers: {}", httpHeadersStr);
        }

        // Add request ID to the response headers
        httpResponse.setHeader("X-Request-ID", requestId);

        // Compact the JSON string and add into Thread Local Context
        String body = wrappedRequest.getBody();
        if (body != null && !body.isEmpty() && isValidJson(body)) {
            String compactJson = compactJson(body, objectMapper);
            RequestContext.setBody(compactJson);
        }
        System.out.println("body " + body);

        try {
            // Proceed with the filter chain
            chain.doFilter(wrappedRequest, response);
        } catch (Exception e) {
            logger.error("Error occurred while processing request: {}", e.getMessage(), e);
            System.out.println("classname: " + e.getClass());
            throw e; // Re-throw the exception to ensure proper error handling
        } finally {
            // Clean up RequestContext to prevent memory leaks
            RequestContext.clear();
            MDC.remove("requestId");
        }
    }

    @Override
    public void destroy() {
        // Clean-up code if needed
    }

    private String getHttpHeadersString(HttpServletRequest httpRequest) {
        StringBuilder headersString = new StringBuilder("[");

        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);

            headersString.append(headerName)
                    .append(":\"")
                    .append(headerValue)
                    .append("\", ");
        }

        // Remove the trailing comma and space, and close the bracket
        if (headersString.length() > 1) {
            headersString.setLength(headersString.length() - 2); // Remove last ", "
        }
        headersString.append("]");
        return headersString.toString();
    }

    private String compactJson(String jsonString, ObjectMapper objectMapper) {
        try {
            // Convert JSON string to an Object
            Object jsonObject = objectMapper.readValue(jsonString, Object.class);

            // Convert Object back to a compact JSON string
            return objectMapper.writeValueAsString(jsonObject);
        } catch (IOException e) {
            logger.error("Error processing json: {}", e.getMessage(), e);
        }
        return null;
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
