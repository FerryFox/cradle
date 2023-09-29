package com.fox.cradle.globalconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.fox.cradle.configuration.security",
        "com.fox.cradle.features.appuser",
        "com.fox.cradle.features.stampSystem"
})
@EnableMongoRepositories(basePackages = {
        "com.fox.cradle.features.picture",
        "com.fox.cradle.logging",
        "com.fox.cradle.features.news",
})
public class DataConfig {
}
