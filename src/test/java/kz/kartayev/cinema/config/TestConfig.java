package kz.kartayev.cinema.config;

import kz.kartayev.cinema.repository.PersonRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {
  @Bean
  @Primary
  public PersonRepository getUserRepository() {
    return Mockito.mock(PersonRepository.class);
  }
}
