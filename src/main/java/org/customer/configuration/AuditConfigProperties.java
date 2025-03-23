package org.customer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties
@Component
public class AuditConfigProperties {

	//@Value("${spring.kafka.template.default-topic}")
	public String topicName;
	@Bean
	public String getTopicName() {
		return topicName;
	}

}
