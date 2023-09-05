package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final String VALID_LOGIN = "Alexandr";
    private static final String INVALID_LOGIN = "Alex";
    private static final String VALID_PASSWORD = "Alex12345";
    private static final String INVALID_PASSWORD = "12345";
    private static final int VALID_AGE = 20;
    private static final int INVALID_AGE = 17;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void validRegistration_Ok() throws UserInvalidDataException {
        registrationService.register(user);
    }

    @Test
    void duplicatesLogin_NotOk() throws UserInvalidDataException {
        registrationService.register(user);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void shortLogin_NotOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void youngAge_NotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyPassword_NotOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user));
    }
}
