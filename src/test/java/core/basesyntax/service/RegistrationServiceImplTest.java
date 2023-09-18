package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("bayraktar");
        user.setLogin("gooodlogin@gmail.com");
        user.setAge(25);
    }

    @Test
    void register_loginExist_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_checkAge_Ok() {
        user.setAge(56);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_checkAge_NotOk() {
        user.setAge(3);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNull_NotOk() {
        User userOne = new User();
        userOne.setAge(null);
        userOne.setPassword("12346987");
        userOne.setLogin("goodloginn@gmail.com");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userOne);
        });
    }

    @Test
    void register_checkPassword_Ok() throws RegistrationException {
        user.setPassword("yuliaka");
        registrationService.register(user);
    }

    @Test
    void register_checkPasswordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_checkPasswordIsShort_NotOk() {
        user.setPassword("jawelin");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_CheckPLoginEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_CheckPLoginLessThatMinCharacters_NotOk() {
        user.setLogin("user");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_CheckLogin_Ok() {
        user.setLogin("iloveukraine");
        registrationService.register(user);
    }

    @Test
    void register_CheckPasswordNull_Ok() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_checkLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
