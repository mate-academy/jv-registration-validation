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
    private static RegistrationService registrationService;
    private static User user;
    private static final String TEST_LOGIN = "Oleg";
    private static final String TEST_PASSWORD = "123456";
    private static final int TEST_AGE = 19;
    private static final String INVALID_TEST_PASSWORD = "12345";
    private static final int INVALID_TEST_AGE = 17;

    @BeforeAll
    static void createService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setAge(TEST_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(null);
        });
    }

    @Test
    void nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void lengthOfPassword_notOk() {
        user.setPassword(INVALID_TEST_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void toLowAge_notOk() {
        user.setAge(INVALID_TEST_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAlreadyExist_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNewUser_ok() {
        assertEquals(user, registrationService.register(user));
    }
}
