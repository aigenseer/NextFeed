package com.nextfeed.service.manager.user;

import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.core.service.manager.UserManagerService;
import com.nextfeed.library.core.service.manager.dto.user.NewUserRequest;
import com.nextfeed.library.core.service.manager.dto.user.ValidateUserRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user-manager")
public class UserManagerRestController implements UserManagerService {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerRestController.class, args);
    }

    private final UserManager userManager;

    @RequestMapping(value = "/v1/user/create", method = RequestMethod.POST)
    public User createUser(@RequestBody NewUserRequest request) {
        return userManager.createUser(request.getMailAddress(), request.getName(), request.getPw());
    }

    @RequestMapping(value = "/v1/user/id/{userId}", method = RequestMethod.GET)
    public User createUser(@PathVariable("userId") Integer userId) {
        return userManager.getUserById(userId);
    }

    @RequestMapping(value = "/v1/user/mail-address/{mailAddress}", method = RequestMethod.GET)
    public User getUserByMailAddress(@PathVariable("mailAddress") String mailAddress) {
        return userManager.getUserByMailAddress(mailAddress);
    }

    @RequestMapping(value = "/v1/user/validate", method = RequestMethod.POST)
    public Boolean validatePasswordByMailAddress(@RequestBody ValidateUserRequest request) {
        return userManager.validatePasswordByMailAddress(request.getMailAddress(), request.getPw());
    }



}
