package hr.fer.zemris.java.hw15.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter which uses {@link JPAEMProvider}.close method to comming transaction
 * and close resource.
 * 
 * @author tin
 *
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } finally {
      JPAEMProvider.close();
    }
  }

  @Override
  public void destroy() {
  }

}