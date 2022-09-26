package org.aibles.authentication.configuration;

import lombok.RequiredArgsConstructor;
import org.aibles.authentication.filter.UserInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RequiredArgsConstructor
public class InterceptorRegistryConfiguration extends WebMvcConfigurerAdapter {
  private final UserInterceptor userInterceptor;

  /**
   * đăng kí interceptor với InterceptorRegistry
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(userInterceptor);
  }
}