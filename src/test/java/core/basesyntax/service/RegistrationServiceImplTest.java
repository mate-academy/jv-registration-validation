package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
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
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userIsAdult_ok() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userAgeLess18_notOk() {
        user.setAge(15);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setAge(null);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userPasswordIsValid_ok() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("nvfjkd");
        user.setLogin(DEFAULT_LOGIN);
        User expected = user;
        User actual = registrationService.register(user);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_userPasswordShort_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword("hdbf");
        user.setLogin(DEFAULT_LOGIN);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(null);
        user.setLogin(DEFAULT_LOGIN);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_passwordIs2Null_notOk() {
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
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
    void getPassword_passwordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }
}
