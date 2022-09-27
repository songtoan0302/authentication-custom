package org.aibles.authentication.configuration;

import org.aibles.authentication.interceptor.UserInterceptor;
import org.aibles.authentication.repository.UserRepository;
import org.aibles.authentication.service.UserService;
import org.aibles.authentication.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"org.aibles.authentication.repository"})
@EnableJpaRepositories(basePackages = {"org.aibles.authentication.repository"})
public class AuthenticationConfiguration {

  @Bean
  public UserService userService(UserRepository userRepository, ModelMapper modelMapper) {
    return new UserServiceImpl(userRepository, modelMapper);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public InterceptorRegistryConfiguration interceptorRegistry(UserInterceptor userInterceptor) {
    return new InterceptorRegistryConfiguration(userInterceptor);
  }

  @Bean
  public UserInterceptor userInterceptor(UserService service) {
    return new UserInterceptor(service);
  }
}
