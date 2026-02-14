package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void allDataIsValid_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("validLogin", registeredUser.getLogin());
    }

    @Test
    void invalidAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(3);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLengthIsLessThanValid_notOk() {
        User user = new User();
        user.setLogin("val");
        user.setPassword("123456");
        user.setAge(19);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginStringIsEmpty_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("12345678");
        user.setAge(19);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_notOk() {
        User user = new User();
        user.setPassword("12345678");
        user.setAge(20);
        user.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userWithLoginAlreadyExist_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("1234567");
        user.setAge(20);

        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLengthIsLessThanValid_notOK() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(19);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordStringIsEmpty_notOK() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(19);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
