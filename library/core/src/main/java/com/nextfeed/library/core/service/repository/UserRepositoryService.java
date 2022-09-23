package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "user-repository-service-x", url = "${nextfeed.service.user-repository-service.domain}:${nextfeed.service.user-repository-service.port}", path = "/api/user-repository")
public interface UserRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public User save(@RequestBody User User);

    @RequestMapping(value = "/v1/get/{userId}", method = RequestMethod.GET)
    public User findById(@PathVariable("userId") Integer userId);

    @RequestMapping(value = "/v1/get/mailaddress", method = RequestMethod.POST)
    public User getUsersByMailAddress(@RequestBody String mailAddress);

}
