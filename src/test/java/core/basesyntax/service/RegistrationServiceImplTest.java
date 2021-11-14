package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegistrationServiceImplTest {
  private StorageDao dao = new StorageDaoImpl();
  private RegistrationService registrationService = new RegistrationServiceImpl();
  private User user;
  private int nubmber = 1;

  @Before
  public void setupNewUser() {
    user = new User();
    user.setId((long) nubmber);
    user.setLogin("userLogin" + nubmber);
    user.setPassword("abcdef" + nubmber);
    user.setAge(18 + nubmber % 90);
    nubmber++;
  }

  @Test
  public void register_userIsNull_ThrowMessage() {
    Exception exception = generateException(null);
    Assert.assertEquals("Incorrect entry about user", exception.getMessage());
  }

  @Test
  public void register_loginIsNull_ThrowMessage() {
    user.setLogin(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void register_passwordIsNull_ThrowMessage() {
    user.setPassword(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's login or password is absent", exception.getMessage());
  }

  @Test
  public void register_passwordIsShort_ThrowMessage() {
    user.setPassword("3");
    Exception exception = generateException(user);
    Assert.assertEquals("User's password is very short", exception.getMessage());
  }

  @Test
  public void register_ageIsEmptyThrowMessage(){
    user.setAge(null);
    Exception exception = generateException(user);
    Assert.assertEquals("User's age is empty", exception.getMessage());

  }

  @Test
  public void register_loginIsExit_ThrowMessage() {
    User john = new User();
    john.setId(16L);
    john.setLogin("J");
    john.setPassword("John2005");
    john.setAge(22);
    dao.add(john);
    user.setLogin("J");
    Assert.assertEquals("Login is exist!", generateException(user).getMessage());
  }

  @Test
  public void register_UserIsNotAdult_ThrowMessage() {
    user.setAge(10);
    Assert.assertEquals("User is not adult!", generateException(user).getMessage());
  }

  @Test
  public void register_incorrectAge_ThrowMessage() {
    user.setAge(1000);
    Assert.assertEquals("Incorrect user's age. Age =" + user.getAge(), generateException(user).getMessage());
    user.setAge(-5);
    Assert.assertEquals("Incorrect user's age. Age =" + user.getAge(), generateException(user).getMessage());
  }

  @Test
  public void register_userDataIsOk_AddToStorage() {
    registrationService.register(user);
    Assert.assertEquals(user, dao.get(user.getLogin()));
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