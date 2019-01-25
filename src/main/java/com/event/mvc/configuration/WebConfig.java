package com.event.mvc.configuration;

import com.event.mvc.configuration.formatter.DateFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@ComponentScan("com.event.mvc")
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {
   private ApplicationContext applicationContext;

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
   }

   @Bean
   public DateFormatter dateFormatter() {
      return new DateFormatter();
   }

   @Override
   public void addFormatters(FormatterRegistry registry) {
      registry.addFormatter(dateFormatter());
   }

   @Bean
   public SpringResourceTemplateResolver templateResolver() {
      SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
      templateResolver.setApplicationContext(applicationContext);
      templateResolver.setPrefix("templates/");
      templateResolver.setSuffix(".html");


      templateResolver.setTemplateMode(TemplateMode.HTML);
      templateResolver.setCacheable(false);
      return templateResolver;
   }

   @Bean
   public SpringTemplateEngine springTemplateEngine() {
      SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
      springTemplateEngine.addDialect(java8TimeDialect());
      springTemplateEngine.setTemplateResolver(templateResolver());
      springTemplateEngine.setEnableSpringELCompiler(false);

      return springTemplateEngine;
   }

   @Bean
   public ThymeleafViewResolver viewResolver() {
      ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
      viewResolver.setTemplateEngine(springTemplateEngine());
      return viewResolver;
   }

   @Bean
   public Java8TimeDialect java8TimeDialect() {
      return new Java8TimeDialect();
   }

}
