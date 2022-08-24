package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "survey-template-manager-service")
@LoadBalancerClient(name = "survey-template-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SurveyTemplateManagerService {

}
