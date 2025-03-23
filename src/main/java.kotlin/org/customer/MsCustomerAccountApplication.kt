package org.customer

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.kafka.annotation.EnableKafka


@SpringBootApplication
@EnableCaching
@EnableKafka
@OpenAPIDefinition(
    info = Info(
        title = "Accounts Service API",
        version = "1.0",
        description = "API for managing accounts and transactions with Choreography-based Saga pattern"
    )
)
@ImportAutoConfiguration(
    classes = [CacheAutoConfiguration::class, RedisAutoConfiguration::class
    ]
)
@ComponentScan(
    basePackages = ["org.customer"],
    excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)]
)
object MsCustomerAccountApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(MsCustomerAccountApplication::class.java, *args)
    }
}