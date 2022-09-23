package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.system.SystemConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "system-repository-service-x", url = "${nextfeed.service.system-repository-service.domain}:${nextfeed.service.system-repository-service.port}", path = "/api/system-repository")
public interface SystemRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public SystemConfiguration save(@RequestBody SystemConfiguration systemConfiguration);

    @RequestMapping(value = "/v1/get/name", method = RequestMethod.POST)
    public Optional<SystemConfiguration> getByName(@RequestBody String name);

}
