package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegisterDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final int NEGATIVE_AGE = -1;

    private RegistrationServiceImpl registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User("testing@gmail.com", "123456", VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerValidUser_Ok() {
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void alreadyRegisteredLogin_NotOk() {
        Storage.people.add(user);
        User invalidUser = new User("testing@gmail.com", "123456", VALID_AGE);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void negativeUserAge_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shortUserLogin_NotOk() {
        user.setLogin("regis");
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void shortUserPassword_NotOk() {
        user.setPassword("test");
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userUnderage_NotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
