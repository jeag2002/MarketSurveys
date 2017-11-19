package com.example.springbootzuulgatwayproxy.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteFilter extends ZuulFilter {
	
  private final Logger logger = LoggerFactory.getLogger(RouteFilter.class);	

  @Override
  public String filterType() {
    return "route";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    logger.info("[RouteFilter] Inside Route Filter");
    RequestContext ctx = RequestContext.getCurrentContext();

    return null;
  }

}