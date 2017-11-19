package com.example.springbootzuulgatwayproxy.filters;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

public class PreFilter extends ZuulFilter {
	
  private final Logger logger = LoggerFactory.getLogger(PreFilter.class);	

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 10;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    logger.info("[PreFilter] Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
    
    if (request.getMethod().equalsIgnoreCase("POST")){
    	
    	String url = request.getRequestURL().toString();
    	String funct = url.substring(url.lastIndexOf("/"));
    	
    	if (url.lastIndexOf("/")!=-1){
	    	if (funct.equalsIgnoreCase("/getmarketsurveyslist")){
		    	try{
		    		ctx.setRouteHost(new URL("http://localhost:8090/getmarketsurveyslist"));
		    		logger.info("[Prefilter] new host redirection (" + ctx.getRouteHost() + ")");
		    		
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}else if (funct.equalsIgnoreCase("/asyncgetmarketsurveyslist")){
	    		try{
		    		ctx.setRouteHost(new URL("http://localhost:8090/asyncgetmarketsurveyslist"));
		    		logger.info("[Prefilter] new host redirection (" + ctx.getRouteHost() + ")");
		    		
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
    	}
    	
    	
    	
    }

    return null;
  }

}