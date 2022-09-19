package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.core.service.manager.dto.user.NewUserRequest;
import com.nextfeed.library.core.service.manager.dto.user.ValidateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-manager-service-x", url = "${nextfeed.service.user-manager-service.domain}:${nextfeed.service.user-manager-service.port}", path = "/api/user-manager")
public interface UserManagerService {

    @RequestMapping(value = "/v1/user/create", method = RequestMethod.POST)
    public User createUser(@RequestBody NewUserRequest request);

    @RequestMapping(value = "/v1/user/id/{userId}", method = RequestMethod.GET)
    public User createUser(@PathVariable("userId") Integer userId);

    @RequestMapping(value = "/v1/user/mail-address/{mailAddress}", method = RequestMethod.GET)
    public User getUserByMailAddress(@PathVariable("mailAddress") String mailAddress);

    @RequestMapping(value = "/v1/user/validate", method = RequestMethod.POST)
    public Boolean validatePasswordByMailAddress(@RequestBody ValidateUserRequest request);

}
