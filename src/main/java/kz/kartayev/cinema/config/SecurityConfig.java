package kz.kartayev.cinema.config;

import kz.kartayev.cinema.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for Cinema Center Application.
 * */
@EnableWebSecurity
public class SecurityConfig {
  private final PersonDetailsService personDetailsService;

  @Autowired
  public SecurityConfig(PersonDetailsService personDetailsService) {
    this.personDetailsService = personDetailsService;
  }


  /**
   * Configuration in system.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.httpBasic()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .logout().logoutUrl("/logout").and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/registration").permitAll()
            .anyRequest().authenticated().and().build();
  }

  /**
   * Authentication in system.
   * */
  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(personDetailsService)
            // при аутентификации Spring Security будет автоматически прогонять через
            // BCryptPasswordEncoder
            .passwordEncoder(getPasswordEncoder())
            .and()
            .build();
  }

  /**
   * Bean for encode password with encoder classes.
   * */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
