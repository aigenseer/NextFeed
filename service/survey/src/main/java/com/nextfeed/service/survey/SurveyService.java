package com.nextfeed.service.survey;


import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "survey-service", fallback = SurveyFallBackController.class)
@LoadBalancerClient(name = "customer-service", configuration= LoadBalancerConfiguration.class)
public interface SurveyService {

    @RequestMapping("/survey/test")
    public TestRequest create();

}