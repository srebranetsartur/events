package com.event.mvc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaServiceConfig.class)
public class AppConfig {

}
