package com.nextfeed.service.manager.session.kafaconfig.initionaliser;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "nextfeed.kafka")
public class ReplyConfigProperties {
    public Map<String, ReplyConfigProperty> relays;
    public String test;
//    public Object relays;
}
