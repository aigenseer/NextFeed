package com.nextfeed.service.manager.user;

import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.service.manager.UserManagerService;
import com.nextfeed.library.core.service.manager.dto.user.NewUserRequest;
import com.nextfeed.library.core.service.manager.dto.user.ValidateUserRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
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
