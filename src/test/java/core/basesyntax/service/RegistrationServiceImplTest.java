package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationServiceImplTest {
  private final RegistrationService registrationService = new RegistrationServiceImpl();
  private User user;

  @Before
  public void setupUser() {
    user = new User();
    user.setLogin("login");
    user.setPassword("1234567p");
    user.setAge(20);
  }

  @Test
  public void register_userIsNull_ThrowMessage() {
    Exception exception = generateRuntimeException(null);
    Assert.assertEquals("Incorrect entry about user", exception.getMessage());
  }

  @Test
  public void register_loginIsNull_ThrowMessage() {
    user.setLogin(null);
    Exception exception = generateRuntimeException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void register_passwordIsNull_ThrowMessage() {
    user.setPassword(null);
    Exception exception = generateRuntimeException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void register_passwordIsShort_ThrowMessage() {
    user.setPassword("3");
    Exception exception = generateRuntimeException(user);
    Assert.assertEquals("User's password is very short", exception.getMessage());
  }

  @Test
  public void register_ageIsEmptyThrowMessage(){
    user.setAge(null);
    Exception exception = generateRuntimeException(user);
    Assert.assertEquals("User's age is empty", exception.getMessage());

  }

  @Test
  public void register_loginIsExit_ThrowMessage() {
    User john = new User();
    john.setId(16L);
    john.setLogin("J");
    john.setPassword("John2005");
    john.setAge(22);
    Storage.people.add(john);
    user.setLogin("J");
    Assert.assertEquals("Login is exist!", generateRuntimeException(user).getMessage());
  }

  @Test
  public void register_UserIsNotAdult_ThrowMessage() {
    user.setAge(10);
    Assert.assertEquals("User is not adult!", generateRuntimeException(user).getMessage());
  }

  @Test
  public void register_incorrectAge_ThrowMessage() {
    user.setAge(-5);
    Assert.assertEquals("Incorrect user's age. Age =" + user.getAge(), generateRuntimeException(user).getMessage());
  }

  @Test
  public void register_userDataIsOk_AddToStorage() {
    registrationService.register(user);
    Assert.assertTrue(Storage.people.contains(user));
  }

  private Exception generateRuntimeException(User user) {
    RuntimeException exception = new RuntimeException();
    try {
      registrationService.register(user);
    } catch (RuntimeException exc) {
      exception = exc;
    }
    return exception;
  }
}