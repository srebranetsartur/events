package com.event.mvc.configuration;

import org.springframework.context.annotation.*;

@Configuration
@Import(JpaDataSourceConfig.class)
@ComponentScan({"com.event.mvc.dao", "com.event.mvc.service"})
public class JpaServiceConfig {

}
