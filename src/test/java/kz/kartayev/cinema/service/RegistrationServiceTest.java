package kz.kartayev.cinema.service;

import kz.kartayev.cinema.CinemaCenterApplication;
import kz.kartayev.cinema.model.Person;
import kz.kartayev.cinema.util.ErrorMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaCenterApplication.class)
public class RegistrationServiceTest {
  @Autowired
  RegistrationService registrationService;
  @Test
  public void newUserShouldHaveCorrectCardAndPassword() throws ErrorMessage{
    Person person = new Person();
    person.setUsername("Tamerlan");
    person.setAge(19);
    person.setRole("ROLE_ADMIN");
    person.setCard("00000000000000");
    person.setWallet(5000);
    person.setPassword("salam");
    Assert.assertThrows("incorrect password", Exception.class,
            () -> registrationService.register(person));
  }
}
