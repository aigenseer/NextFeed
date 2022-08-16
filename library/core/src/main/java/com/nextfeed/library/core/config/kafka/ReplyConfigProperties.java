package com.nextfeed.library.core.config.kafka;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "nextfeed.kafka")
public class ReplyConfigProperties {
    public Map<String, ReplyConfigProperty> relays;
}
