package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(20);
        user.setLogin("qwerty");
        user.setPassword("123456");
        Storage.people.clear();
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_loginIsNull_not_ok() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_not_ok() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_not_ok() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_not_ok() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_password_not_ok() {
        user.setPassword("12345");
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_loginExist_not_ok() {
        registrationService.register(user);
        Assertions.assertThrows(RuntimeException.class, ()-> registrationService.register(user));
    }

    @Test
    void register_id_not_ok() {
        user.setId(0L);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        user.setAge(20);
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_password_ok() {
        user.setLogin("qwerty");
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }
}
