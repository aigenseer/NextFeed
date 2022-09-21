package com.nextfeed.library.core.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty({"nextfeed.metrics.enabled"})
public class RequestPrometheusCounter implements Filter {

    private Counter reqCounter;
    private final MeterRegistry registry;

    @Value("${nextfeed.kubernetes.namespace}")
    private String NAMESPACE;

    @PostConstruct
    public void init(){
        reqCounter = registry.counter("%s_service_metrics".formatted(NAMESPACE), "usecase", "http-request");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if(path.contains("/api/"))
            reqCounter.increment();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}
