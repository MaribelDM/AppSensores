package com.microservicio.app.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//public class CORSFilter {}

@Component
public class CORSFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse res = (HttpServletResponse) response;
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Credentials", "true");
    res.setHeader("Access-Control-Allow-Methods", "*");
    res.setHeader("Access-Control-Max-Age", "*");
    res.setHeader("Access-Control-Allow-Headers", "*");
    chain.doFilter(request, res);
  }

  @Override
  public void destroy() {
  }

  @Override

  public void init(FilterConfig filterConfig) {

  }
  

}