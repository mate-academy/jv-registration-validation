package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidUserException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Long VALID_ID = 1L;
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "newUser";
    private static final String VALID_PASSWORD = "newUser";

    private static final int INVALID_AGE = 15;
    private static final String INVALID_LOGIN = "mate";
    private static final String INVALID_PASSWORD = "mate";

    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setId(VALID_ID);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void validUserIsCreated_Ok() {
        User actual = registrationService.register(user);
        assertTrue(actual.equals(user));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAlreadyExists_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooShortLogin_NotOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooShortPassword_NotOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooYoungUser_NotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
