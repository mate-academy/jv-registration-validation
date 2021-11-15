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
  private static User user1;
  private static User user2;

  @BeforeAll
  static void beforeAll() {
    registrationService = new RegistrationServiceImpl();
    user1 = new User();
    user2 = new User();
  }

  @BeforeEach
  void setUp() {
    Storage.people.clear();
    user1.setPassword("goodPassword");
    user1.setAge(25);
    user1.setLogin("userLogin");
    user2.setPassword("passwordUser2");
    user2.setLogin("loginUser2");
    user2.setAge(27);
  }

  @Test
  void register_validUser_ok() {
    User expected = user1;
    User actual = registrationService.register(user1);
    assertEquals(expected, actual);
  }

  @Test
  void register_manyUser_ok(){

    assertEquals(registrationService.register(user1), user1);
    assertEquals(registrationService.register(user2), user2);
  }

  @Test
  void register_samePassword_ok(){
    user2.setPassword(user1.getPassword());
    assertEquals(registrationService.register(user1), user1);
    assertEquals(registrationService.register(user2), user2);
  }

  @Test
  public void register_null_notOk() {
    assertThrows(NullPointerException.class, () -> registrationService.register(null), "must be NullPointerException when user1==null");
  }

  @Test
  void register_invalidPassword_notOK() {
    user1.setPassword("1");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
    user1.setPassword(null);
    assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    user1.setPassword("");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
  }

  @Test
  void register_invalidLogin_notOk() {
    user1.setLogin("");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
    user1.setPassword(null);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
    user1.setPassword("");
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
  }

  @Test
  void register_invalidAge() {
    user1.setAge(null);
    assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    user1.setAge(0);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
  }

  @Test
  void register_sameUser_notOk() {
    registrationService.register(user1);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
  }




}
