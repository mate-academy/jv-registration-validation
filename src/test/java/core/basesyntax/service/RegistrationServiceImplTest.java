package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_userAgeLarge18_ok() {
        user.setAge(20);
        user.setPassword("password");
        user.setLogin("login");
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userIsAdult_ok() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("password");
        user.setLogin("login");
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userAgeLess18_notOk() {
        user.setAge(17);
        user.setPassword("password");
        user.setLogin("login");
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userPasswordIsValid_ok() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("nvfjkd");
        user.setLogin("login");
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userPasswordShort_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("hdbf");
        user.setLogin("login");
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("");
        user.setLogin("login");
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(null);
        user.setLogin("login");
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userWithExistLogin_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("password");
        user.setLogin("login");
        registrationService.register(user);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void getAge_ageIsNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void getLogin_loginIsNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void getLogin_loginIsEmpty_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void getPassword_passwordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }
}
