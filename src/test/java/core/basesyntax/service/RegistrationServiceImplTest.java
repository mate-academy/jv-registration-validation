package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static final String DEFAULT_LOGIN = "GiantKIng";
    private static final String DEFAULT_PASSWORD = "GiantKIng";
    private static final int DEFAULT_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void nullValue_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginAlreadyExist_notOk() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsTooShort_notOk() {
        User user = createUser("DEFAU1234512", DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("Def");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsTooShort_notOk() {
        User user = createUser(DEFAULT_LOGIN, "qwe", DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void belowAge_notOk() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, 10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationComplete_ok() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationService.register(user);
        assertEquals(storageDao.get(user.getLogin()), user);
    }

    @Test
    void passwordIsNull_notOk() {
        User user = createUser(DEFAULT_LOGIN, null, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_notOk() {
        User user = createUser(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_notOk() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsTooBig_notOk() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, Integer.MAX_VALUE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNegative_notOk() {
        User user = createUser(DEFAULT_LOGIN, DEFAULT_PASSWORD, -10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void wrongUserLogin_NotOk() {
        User userWithSpaces = createUser("                ", DEFAULT_PASSWORD, DEFAULT_AGE);
        User userOkButSpaces = createUser("giantking   123", DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithSpaces);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userOkButSpaces);
        });
    }

    @Test
    void wrongUserPassword_NotOk() {
        User userOnlySpaces = createUser(DEFAULT_LOGIN, "              ", DEFAULT_AGE);
        User userOkButSpaces = createUser(DEFAULT_LOGIN, "giantk    123", DEFAULT_AGE);
        User userNotEnoughChars = createUser(DEFAULT_LOGIN, "giant1234124", DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userOkButSpaces);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userOnlySpaces);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userNotEnoughChars);
        });
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
