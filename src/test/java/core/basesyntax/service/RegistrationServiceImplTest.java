package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "unuwjf8j2";
    private static final long VALID_ID = 38628L;
    private static final int VALID_AGE = 22;
    private static final String INVALID_LOGIN = "val";
    private static final String INVALID_PASSWORD = "unu";
    private static final int INVALID_AGE = 16;

    private RegistrationService registrationService;
    private User validUser;
    private User NullUser;
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
        validUser = getDefaultValidUser();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_UserNull_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NullUser);
        });
    }

    @Test
    void register_AgeNull_notOk() {
        validUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_AgeInvalid_notOk() {
        validUser.setAge(INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_PasswordNull_notOk() {
        validUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_PasswordShort_notOk() {
        validUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_LoginShort_notOk() {
        validUser.setLogin(INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_LoginNull_notOk() {
        validUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_ValidUserIsAlreadyExist_notOk() {
        Storage.people.add(validUser);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(validUser);
        });
    }
}
