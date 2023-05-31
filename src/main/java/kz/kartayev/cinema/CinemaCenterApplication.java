package kz.kartayev.cinema;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Cinema Center starter class.
 * */
@SpringBootApplication
@EnableSwagger2
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
  public Docket apis() {
    return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("kz.kartayev.cinema")).build();
  }
}
