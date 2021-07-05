package com.egeroo.roocvthree.core.config;

import brave.Tracer;
import brave.Tracing;

import org.apache.log4j.Logger;

//import brave.Tracing;

//import org.keycloak.KeycloakPrincipal;
//
//import org.keycloak.KeycloakSecurityContext;
//
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
//
//import org.keycloak.representations.AccessToken;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;


@Component

public class MDCFilter implements Filter {

	private static final Logger log = Logger.getLogger(MDCFilter.class);	

    @Override

    public void init(FilterConfig filterConfig) 
    		//throws ServletException 
    {

    }



    @Override

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
    //throws RuntimeException, ServletException, IOException

            //throws IOException, ServletException 
    {

        // set zipkin tracing info in request attribute

        // to be logged by tomcat acces log

        // can't use MDC, because by the time access log is printed

        // MDC context has been cleared
    	
    	Tracer tracer = Tracing.currentTracer();

        req.setAttribute("X-B3-TraceId",tracer.currentSpan().context().traceIdString());

        req.setAttribute("X-B3-SpanId", String.format("%016x", tracer.currentSpan().context().spanId()));

        req.setAttribute("X-B3-ParentId", String.format("%016x", tracer.currentSpan().context().parentIdAsLong()));



        // inject ke setiap header response, agar bisa dimunculkan di frontend

        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

        httpServletResponse.setHeader("X-B3-TraceId",tracer.currentSpan().context().traceIdString());

        httpServletResponse.setHeader("X-B3-SpanId", String.format("%016x", tracer.currentSpan().context().spanId()));

        httpServletResponse.setHeader("X-B3-ParentId", String.format("%016x", tracer.currentSpan().context().parentIdAsLong()));



//        Authentication authentication =
//
//                SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication instanceof KeycloakAuthenticationToken) {
//
//            KeycloakPrincipal principal = (KeycloakPrincipal) ((KeycloakAuthenticationToken) authentication).getPrincipal();
//
//            KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
//
//            AccessToken token = session.getToken();
//
//            MDC.put("userName", token.getName());
//
//            // put username information in request attribute for tomcat access logging
//
//            req.setAttribute("userName",token.getName());
//
//        }

        try {

            chain.doFilter(req, resp);

        } catch(Exception ex){
        	log.debug(ex + "error get mdc filter");
        }
        finally {

//            if (authentication != null) {
//
//                MDC.remove("userName");
//
//            }

        }

    }



    @Override

    public void destroy() {

    }



}