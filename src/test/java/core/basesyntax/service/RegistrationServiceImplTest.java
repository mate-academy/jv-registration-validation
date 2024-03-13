package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "unuwjf8j2";
    private static final long VALID_ID = 38628L;
    private static final int VALID_AGE = 22;
    private static final String INVALID_LOGIN = "val";
    private static final String INVALID_PASSWORD = "unu";
    private static final int INVALID_AGE = 16;

    private RegistrationService registrationService;
    private User user;

    private User getDefaultValidUser() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setId(VALID_ID);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        return user;
    }

    @BeforeEach
    void setUp() {
        user = getDefaultValidUser();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_UserNull_notOk() {
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
        assertEquals("Ooops, user is not exist", exception.getMessage());
    }

    @Test
    void register_AgeNull_notOk() {
        user.setAge(null);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Oops, age is not exist", exception.getMessage());
    }

    @Test
    void register_AgeInvalid_notOk() {
        user.setAge(INVALID_AGE);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Sorry, for registration you must be at least "
                + MIN_AGE + " years", exception.getMessage());
    }

    @Test
    void register_PasswordNull_notOk() {
        user.setPassword(null);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Your password is not exist", exception.getMessage());
    }

    @Test
    void register_PasswordShort_notOk() {
        user.setPassword(INVALID_PASSWORD);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Your password is too short, must be at least "
                + MIN_LENGTH + " symbols", exception.getMessage());
    }

    @Test
    void register_LoginShort_notOk() {
        user.setLogin(INVALID_LOGIN);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Your login is too short, must be at least "
                + MIN_LENGTH + " symbols", exception.getMessage());
    }

    @Test
    void register_LoginNull_notOk() {
        user.setLogin(null);
        Throwable exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Your login is not exist", exception.getMessage());
    }

    @Test
    void register_ValidUserIsAlreadyExist_notOk() {
        Storage.people.add(user);
        Throwable ecxeption = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Ooops, user with same login is already exist", ecxeption.getMessage());
    }
}
