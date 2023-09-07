package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Javelin";
    private static final String VALID_PASSWORD = "Java12";
    private static final int VALID_AGE = 18;
    private static final String INVALID_LOGIN = "Java";
    private static final String INVALID_LOGIN_NULL = null;
    private static final String INVALID_LOGIN_EMPTY = "";
    private static final String INVALID_PASSWORD = "Java1";
    private static final String INVALID_PASSWORD_NULL = null;
    private static final String INVALID_PASSWORD_EMPTY = "";
    private static final int INVALID_AGE = 17;
    private static final int INVALID_ZERO_AGE = 0;
    private static final int INVALID_NEGATIVE_AGE = -10;
    private static final Integer INVALID_AGE_NULL = null;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(UserValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(INVALID_LOGIN_NULL);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(INVALID_PASSWORD_NULL);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(INVALID_AGE_NULL);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN_EMPTY);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD_EMPTY);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(INVALID_ZERO_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(INVALID_NEGATIVE_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_suchLoginAlreadyExists_NotOk() {
        Storage.people.add(user);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAddToStorage_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
