package com.example.springbootzuulgatwayproxy.filters;

import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorFilter extends ZuulFilter {
	
  private final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);	

  @Override
  public String filterType() {
    return "error";
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
	logger.info("[ErrorFilter] Inside Route Filter");
    return null;
  }

}