
package com.nextfeed.service.manager.session;

import com.nextfeed.service.manager.session.kafaconfig.initionaliser.ReplyKafkaTemplateAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages={"com.nextfeed.service.manager.session.kafaconfig"})
public class SpringKafkaSynchronousExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaSynchronousExampleApplication.class, args);
	}
}