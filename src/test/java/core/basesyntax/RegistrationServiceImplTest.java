package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.RegistrationUserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {
  private static RegistrationService registrationService;
  private static User firstUser;
  private static User secondUser;

  @BeforeAll
  static void beforeAll() {
    registrationService = new RegistrationServiceImpl();
    firstUser = new User();
    secondUser = new User();
  }

  @BeforeEach
  void setUp() {
    Storage.people.clear();
    firstUser.setPassword("goodPassword");
    firstUser.setAge(25);
    firstUser.setLogin("userLogin");
    secondUser.setPassword("passwordUser2");
    secondUser.setLogin("loginUser2");
    secondUser.setAge(27);
  }

  @Test
  void register_validUser_ok() {
    User expected = firstUser;
    User actual = registrationService.register(firstUser);
    assertEquals(expected, actual);
  }

  @Test
  void register_manyUser_ok(){
    User expectedFirst = firstUser;
    User actualFirst = registrationService.register(firstUser);
    User expectedSecond = secondUser;
    User actualSecond = registrationService.register(secondUser);
    assertEquals(expectedFirst, actualFirst);
    assertEquals(expectedSecond, actualSecond);
  }

  @Test
  void register_samePassword_ok(){
    secondUser.setPassword(firstUser.getPassword());
    User expectedFirst = firstUser;
    User actualFirst = registrationService.register(firstUser);
    User expectedSecond = secondUser;
    User actualSecond = registrationService.register(secondUser);
    assertEquals(actualFirst, expectedFirst);
    assertEquals(actualSecond, expectedSecond);
  }

  @Test
  public void register_null_notOk() {
    assertThrows(RegistrationUserException.class, () -> registrationService.register(null));
  }

  @Test
  void register_invalidPassword_notOK() {
    firstUser.setPassword("12345");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }

  @Test
  void register_nullPassword_notOk() {
    firstUser.setPassword(null);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }

  @Test
  void register_invalidLogin_notOk() {
    firstUser.setLogin("");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }

  @Test
  void register_nullLogin_notOk() {
    firstUser.setPassword(null);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }

  @Test
  void register_nullAge_notOk() {
    firstUser.setAge(null);
    assertThrows(NullPointerException.class, () -> registrationService.register(firstUser));

  }

  @Test
  void register_lessMinAge_NotOk() {
    firstUser.setAge(17);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }

  @Test
  void register_sameUser_notOk() {
    registrationService.register(firstUser);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(firstUser));
  }
}
