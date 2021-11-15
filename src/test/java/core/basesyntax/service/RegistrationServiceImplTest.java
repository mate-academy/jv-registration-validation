package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import org.junit.After;

import java.util.EmptyStackException;

public class RegistrationServiceImplTest {
  private static RegistrationService registrationService;
  private User user;

  @BeforeClass
  public static void initialization() {
    registrationService = new RegistrationServiceImpl();
  }

  @Before
  public void setupUser() {
    user = new User();
    user.setLogin("login");
    user.setPassword("1234567p");
    user.setAge(20);
  }
  
  @After
  public void clearStorage() {
    Storage.people.clear();
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void register_userIsNull_throwException() {
    exception.expect(RuntimeException.class);
    registrationService.register(null);
  }

  @Test
  public void register_loginIsNull_throwException() {
    exception.expect(RuntimeException.class);
    user.setLogin(null);
    registrationService.register(user);
  }

  @Test
  public void register_passwordIsNull_throwException() {
    exception.expect(RuntimeException.class);
    user.setPassword(null);
    registrationService.register(user);
  }

  @Test
  public void register_ageIsNull_throwException() {
    exception.expect(RuntimeException.class);
    user.setAge(null);
    registrationService.register(user);
  }

  @Test
  public void register_passwordIsShort_throwException() {
    exception.expect(RuntimeException.class);
    user.setPassword("12345");
    registrationService.register(user);
  }

  @Test
  public void register_passwordIsOk_addToStorage() {
    user.setPassword("123456");
    Assert.assertEquals(user, registrationService.register(user));
  }

  @Test
  public void register_loginIsExit_throwException() {
    exception.expect(RuntimeException.class);
    User john = new User();
    john.setLogin("login");
    john.setPassword("John2005");
    john.setAge(22);
    registrationService.register(user);
    registrationService.register(john);
  }

  @Test
  public void register_incorrectAge_throwException() {
    exception.expect(RuntimeException.class);
    user.setAge(-5);
    registrationService.register(user);
  }

  @Test
  public void register_userIsNotAdult_throwException() {
    exception.expect(RuntimeException.class);
    user.setAge(17);
    registrationService.register(user);
  }

  @Test
  public void register_userIsAdult_addToStorage() {
    user.setAge(18);
    Assert.assertEquals(user, registrationService.register(user));
  }

  @Test
  public void register_userDataIsOk_addToStorage() {
    registrationService.register(user);
    Assert.assertTrue(Storage.people.contains(user));
  }
}