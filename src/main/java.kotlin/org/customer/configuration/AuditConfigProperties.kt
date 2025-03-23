package org.customer.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@ConfigurationProperties
@Component
class AuditConfigProperties {
    //@Value("${spring.kafka.template.default-topic}")
    @get:Bean
    var topicName: String? = null
}
