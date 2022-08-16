package com.nextfeed.library.core.config.kafka;

import lombok.Data;

@Data
public class ReplyConfigProperty {
    public String beanName;
    public String requestTopic;
    public String replyTopic;
    public String groupId;
}
