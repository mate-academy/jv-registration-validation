package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(20);
        user.setLogin("qwerty");
        user.setPassword("123456");
        Storage.people.clear();
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_loginExist_notOk() {
        registrationService.register(user);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }
    
    @Test
    void register_validAge_ok() {
        user.setAge(18);
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_validPassword_ok() {
        user.setLogin("qwerty");
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_validUser_ok() {
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }
}
