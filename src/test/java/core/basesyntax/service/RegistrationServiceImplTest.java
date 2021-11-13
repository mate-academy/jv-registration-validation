package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class RegistrationServiceImplTest {
  private RegistrationService registrationService = new RegistrationServiceImpl();
  private User user;
  private int nubmber = 1;

  @BeforeAll
  static void storageInitialization() {
    User marry = new User();
    marry.setId(1L);
    marry.setLogin("marry123");
    marry.setPassword("qwertyqwerty");
    marry.setAge(33);
    User bob = new User();
    bob.setId(12L);
    bob.setLogin("Bobchich");
    bob.setPassword("qqqaaazzz");
    bob.setAge(18);
    User john = new User();
    john.setId(16L);
    john.setLogin("J");
    john.setPassword("John2005");
    john.setAge(22);
    new StorageDaoImpl().add(marry);
    new StorageDaoImpl().add(bob);
    new StorageDaoImpl().add(john);
  }

  @BeforeEach
  void setupNewUser() {
    System.out.println("BeforeEach");
    user = new User();
    user.setId((long) nubmber);
    user.setLogin("userLogin" + nubmber);
    user.setPassword("abcdef" + nubmber);
    user.setAge(18 + nubmber % 50);
    nubmber++;
  }

  @Test
  public void userIsNullThrowMessage() {
    Exception exception = generateException(null);
    Assert.assertEquals("Incorrect entry about user", exception.getMessage());
  }

  @Test
  public void loginIsNullThrowMessage() {
    user.setLogin(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void passwordIsNullThrowMessage() {
    user.setPassword(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void passwordIsShortThrowMessage() {
    user.setPassword("3");
    Exception exception = generateException(user);
    Assert.assertEquals("User's password is very short", exception.getMessage());
  }

  @Test
  public void ageIsEmptyThrowMassege(){
    user.setAge(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's age is empty", exception.getMessage());

  }

  private Exception generateException(User user) {
    Exception exception = new Exception();
    try {
      registrationService.register(user);
    } catch (Exception exc) {
      exception = exc;
    }
    return exception;
  }
}