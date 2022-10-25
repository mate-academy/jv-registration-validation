package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final int NEGATIVE_AGE = -5433;
    private static final String DEFAULT_LOGIN = "defaultLogin";
    private static final String VALID_PASSWORD = "defaultPassword";
    private static final String INVALID_PASSWORD = "aaaaa";
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        testUser = new User();
        testUser.setLogin(DEFAULT_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userAgeIsNull_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userLoginIsNull_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_withExistingLogin_notOk() {
        Storage.people.add(testUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeInvalid_notOk() {
        testUser.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordIsInvalid_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_withValidData_ok() {
        assertEquals(testUser, registrationService.register(testUser));
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
