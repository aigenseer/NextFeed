package com.nextfeed.service.external.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        String queryString = ((HttpServletRequest)request).getQueryString();
        if(queryString != null){
            logger.info(url + "?" + queryString);
        }else{
            logger.info(url);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}
