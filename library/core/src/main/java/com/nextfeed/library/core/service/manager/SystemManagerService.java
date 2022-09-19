package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.SystemConfiguration;
import com.nextfeed.library.core.service.manager.dto.system.GetConfigurationRequest;
import com.nextfeed.library.core.service.manager.dto.system.NewConfigurationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "system-manager-service-x", url = "${nextfeed.service.system-manager-service.domain}:${nextfeed.service.system-manager-service.port}", path = "/api/system-manager")
public interface SystemManagerService {

    @RequestMapping(value = "/v1/system/create", method = RequestMethod.POST)
    public SystemConfiguration create(@RequestBody NewConfigurationRequest request);

    @RequestMapping(value = "/v1/system/get", method = RequestMethod.POST)
    public SystemConfiguration get(@RequestBody GetConfigurationRequest request);

}
