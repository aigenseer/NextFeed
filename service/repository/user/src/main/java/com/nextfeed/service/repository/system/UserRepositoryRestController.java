package com.nextfeed.service.repository.system;

import com.nextfeed.library.core.entity.User;
import com.nextfeed.library.core.service.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user-repository")
public class UserRepositoryRestController implements UserRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(UserRepositoryRestController.class, args);
    }

    private final UserDBService userDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public User save(@RequestBody User User) {
        return userDBService.save(User);
    }

    @RequestMapping(value = "/v1/get/{userId}", method = RequestMethod.GET)
    public User findById(@PathVariable("userId") Integer userId) {
        return userDBService.findById(userId);
    }

    @RequestMapping(value = "/v1/get/mailaddress", method = RequestMethod.POST)
    public User getUsersByMailAddress(@RequestBody String mailAddress) {
        return userDBService.getUsersByMailAddress(mailAddress).orElseGet(null);
    }

}
