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
    public static final int VALID_AGE = 18;
    public static final int INVALID_AGE = 16;
    private RegistrationServiceImpl registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin("testing@gmail.com");
        user.setPassword("123456");
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
    void registerExistedLogin_NotOk() {
        registrationService.register(user);
        User invalidUser = new User();
        invalidUser.setPassword("123456");
        invalidUser.setAge(VALID_AGE);
        invalidUser.setLogin("testing@gmail.com");
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void smallLogin_NotOk() {
        user.setLogin("test");
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void smallPassword_NotOk() {
        user.setPassword("test");
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void smallAge_NotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidRegisterDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
