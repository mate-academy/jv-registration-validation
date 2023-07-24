package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final Integer VALID_AGE = 20;
    private static final String INVALID_LOGIN = "v";
    private static final String INVALID_PASSWORD = "p";
    private static final Integer INVALID_AGE = 10;
    private static final int PASSWORD_MINIMAL_LENGTH = 6;
    private static final int LOGIN_MINIMAL_LENGTH = 6;
    private static final int USER_MINIMAL_AGE = 18;
    private static StorageDao storage;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void cleanStorage() {
        Storage.people.clear();
    }

    @Test
    void registration_validUserData() {
        User validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationService.register(validUser);
        User userFromStorage = storage.get(validUser.getLogin());
        assertEquals(validUser, userFromStorage, "User data is invalid!");
    }

    @Test
    void registration_existingUser_notOk() {
        User existingUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationService.register(existingUser);
        assertNotNull(storage.get(existingUser.getLogin()));
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(existingUser);
        });
        String expectedMessage = "The user already exists in storage!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userAgeLessThan18_notOk() {
        User underAgeUser = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(underAgeUser);
        });
        String expectedMessage = "To register user must be older than "
                + USER_MINIMAL_AGE + " years!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userLoginInvalid_notOk() {
        User invalidLoginUser = new User(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidLoginUser);
        });
        String expectedMessage = "Login has to be " + LOGIN_MINIMAL_LENGTH
                + " letters or longer!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userPasswordInvalid_notOk() {
        User invalidPasswordUser = new User(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidPasswordUser);
        });
        String expectedMessage = "Password has to be " + PASSWORD_MINIMAL_LENGTH
                + " chars or longer!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userNull_notOk() {
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
        String expectedMessage = "User cannot be null!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userLoginNull_notOk() {
        User loginNullUser = new User(null, VALID_PASSWORD, VALID_AGE);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(loginNullUser);
        });
        String expectedMessage = "User login cannot be null!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userPasswordNull_notOk() {
        User passwordNullUser = new User(VALID_LOGIN, null, VALID_AGE);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(passwordNullUser);
        });
        String expectedMessage = "User password cannot be null!";
        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void registration_userAgeNull_notOk() {
        User ageNullUser = new User(VALID_LOGIN, VALID_PASSWORD, null);
        RegistrationException actual = assertThrows(RegistrationException.class, () -> {
            registrationService.register(ageNullUser);
        });
        String expectedMessage = "User age cannot be null!";
        assertEquals(expectedMessage, actual.getMessage());
    }
}
