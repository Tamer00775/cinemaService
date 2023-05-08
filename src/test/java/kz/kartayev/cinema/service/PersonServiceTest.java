package kz.kartayev.cinema.service;

import kz.kartayev.cinema.CinemaCenterApplication;
import kz.kartayev.cinema.util.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaCenterApplication.class)
public class PersonServiceTest {
  @Autowired
  PersonService personService;
  @Test
  public void getUserByName(){
    String name = "admin";
    Assert.assertThrows("Not correct username", ErrorMessage.class,
            () -> personService.getUserByName(name));
  }
}
