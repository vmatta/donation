package com.donation.configuration;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
    //httpResponse.setHeader("Access-Control-Allow-Methods", "*");
    //httpResponse.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    httpResponse.setHeader("Access-Control-Allow-Headers", "User-Agent,Referer,Origin,Host,Connection,Access-Control-Request-Method,Access-Control-Request-Headers,Cache-Control, Pragma, Expires, Origin,X-Requested-With,Content-Type,Accept,Accept-Encoding,Accept-Language");
    //httpResponse.setHeader("Access-Control-Allow-Headers", "*");
    httpResponse.setHeader("Access-Control-Expose-Headers", "custom-header1, custom-header2");
    httpResponse.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate" );
    httpResponse.setHeader("Pragma", "no-cache");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
    httpResponse.setHeader("Access-Control-Max-Age", "4800");
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }
}  

