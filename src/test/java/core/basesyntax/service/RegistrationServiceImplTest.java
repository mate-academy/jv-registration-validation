package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidUserException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Long VALID_ID = 1L;
    private static final int VALID_AGE_MIN = 18;
    private static final String VALID_LOGIN_MIN = "new111";
    private static final String VALID_PASSWORD_MIN = "new111";
    private static final int VALID_AGE_AVG = 50;
    private static final String VALID_LOGIN_AVG = "new111new111new111new111";
    private static final String VALID_PASSWORD_AVG = "new111new111new111new111";
    private static final int INVALID_AGE_MAX = 17;
    private static final String INVALID_LOGIN_MAX = "mate1";
    private static final String INVALID_PASSWORD_MAX = "mate1";
    private static final int INVALID_AGE_AVG = 13;
    private static final String INVALID_LOGIN_AVG = "mate";
    private static final String INVALID_PASSWORD_AVG = "mate";
    private static final int INVALID_AGE_MIN = 0;
    private static final String INVALID_LOGIN_MIN = "";
    private static final String INVALID_PASSWORD_MIN = "";

    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setId(VALID_ID);
        user.setLogin(VALID_LOGIN_MIN);
        user.setPassword(VALID_PASSWORD_MIN);
        user.setAge(VALID_AGE_MIN);
    }

    @Test
    void register_validUserEdgeCaseIsCreated_Ok() {
        User actual = registrationService.register(user);
        assertTrue(actual.equals(user));
    }

    @Test
    void register_validUserNonEdgeCaseIsCreated_Ok() {
        user.setAge(VALID_AGE_AVG);
        user.setLogin(VALID_LOGIN_AVG);
        user.setPassword(VALID_PASSWORD_AVG);
        User actual = registrationService.register(user);
        assertTrue(actual.equals(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortLoginMaxEdgeCase_NotOk() {
        user.setLogin(INVALID_LOGIN_MAX);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortPasswordMaxEdgeCase_NotOk() {
        user.setPassword(INVALID_PASSWORD_MAX);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooYoungUserMaxEdgeCase_NotOk() {
        user.setAge(INVALID_AGE_MAX);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortLoginAvgEdgeCase_NotOk() {
        user.setLogin(INVALID_LOGIN_AVG);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortPasswordAvgEdgeCase_NotOk() {
        user.setPassword(INVALID_PASSWORD_AVG);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooYoungUserAvgEdgeCase_NotOk() {
        user.setAge(INVALID_AGE_AVG);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortLoginMinEdgeCase_NotOk() {
        user.setLogin(INVALID_LOGIN_MIN);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooShortPasswordMinEdgeCase_NotOk() {
        user.setPassword(INVALID_PASSWORD_MIN);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooYoungUserMinEdgeCase_NotOk() {
        user.setAge(INVALID_AGE_MIN);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
