package com.nextfeed.service.manager.session.kafaconfig.initionaliser;

import lombok.Data;

@Data
public class ReplyConfigProperty {
    public String beanName;
    public String requestTopic;
    public String replyTopic;
    public String groupId;
}
