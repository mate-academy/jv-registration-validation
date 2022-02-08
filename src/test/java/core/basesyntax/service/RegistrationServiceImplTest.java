package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User userLogin = new User();
    private User userGoodPassword = new User();
    private User userGoodAge = new User();

  @BeforeEach
    public void setUp() {
      userLogin.setLogin("user123");
      userGoodAge.setAge(18);
      userGoodPassword.setPassword("123456");
  }

  @AfterEach
  public void cleanDataBase() {
      Storage.people.clear();
    }

    @Test
    public void register_User_ok() {
        User excepted = userLogin;
        User testUser = registrationService.register(excepted);
        assertEquals(excepted, testUser, "Please enter another login");
    }

    @Test
    void register_userBadPassword_notOk() {
        User testUser = userGoodPassword;
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
  }

    @Test
    void register_userWithNullPassword_notOk() {
      User testUser = userGoodPassword;
      testUser.setPassword(null);
      assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userBadAge_notOk() {
        User testUser = userGoodAge;
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
  }

    @Test
    void register_userWithNullAgeValue_notOk() {
        User testUser = userGoodAge;
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    public void register_userLoginAlreadyExit_notOk() {
        Storage.people.add(userLogin);
        User testUser = new User();
        testUser.setLogin(userLogin.getLogin());
        testUser.setPassword("123456");
        testUser.setAge(18);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

}