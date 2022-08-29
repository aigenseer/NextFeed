package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import com.nextfeed.library.core.service.manager.dto.system.GetConfigurationRequest;
import com.nextfeed.library.core.service.manager.dto.system.NewConfigurationRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "system-manager-service")
@LoadBalancerClient(name = "system-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SystemManagerService {

    @RequestMapping(value = "/api/system-manager/v1/system/create", method = RequestMethod.POST)
    public SystemConfiguration create(@RequestBody NewConfigurationRequest request);

    @RequestMapping(value = "/api/system-manager/v1/system/get", method = RequestMethod.POST)
    public SystemConfiguration get(@RequestBody GetConfigurationRequest request);

}
