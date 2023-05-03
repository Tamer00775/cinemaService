package kz.kartayev.cinema;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Cinema Center starter class.
 * */
@SpringBootApplication
public class CinemaCenterApplication {
  public static void main(String[] args) {
    SpringApplication.run(CinemaCenterApplication.class, args);
  }

  /**
   * Bean for modelMapper class.
   * */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
