
package com.nextfeed.service.manager.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.nextfeed.library.core.config.kafka", "com.nextfeed"})
@PropertySource("config.yml")
public class SpringKafkaSynchronousExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaSynchronousExampleApplication.class, args);
	}
}