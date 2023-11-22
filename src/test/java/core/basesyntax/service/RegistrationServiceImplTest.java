package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
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
        user.setAge(20);
        user.setLogin("leonv234@gmail.com");
        user.setPassword("hello5835939");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lengthLogin_notOk() {
        user.setLogin("24536");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lengthPassword_notOk() {
        user.setPassword("final");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_enoughAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsAlreadyExist_notOk() {
        User userSameLogin = new User();
        userSameLogin.setLogin("leonv234@gmail.com");
        Storage.people.add(userSameLogin);
        assertEquals(user.getLogin(), userSameLogin.getLogin());
        Storage.people.remove(userSameLogin);
    }

    @Test
    void register_enoughAge_ok() {
        user.setAge(26);
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lengthLogin_ok() {
        user.setLogin("karlov23456@gmail.com");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LengthPassword_ok() {
        user.setLogin("helloMrJack");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }
}
