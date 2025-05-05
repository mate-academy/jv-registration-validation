package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String SHORT_LOGIN = "short";
    private static final String SHORT_PASSWORD = "small";
    private static final int VALID_AGE = 23;
    private static final int UNDERAGE = 16;
    private static final int NEGATIVE_AGE = -20;
    private static final int ZERO_AGE = 0;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_addNewUser_Ok() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_loginNullValue_NotOk() {
        User user = createNewUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_NotOk() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ShortLogin_NotOk() {
        User user = createNewUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_NotOk() {
        User user = createNewUser("", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ShortPassword_NotOk() {
        User user = createNewUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordNullValue_NotOk() {
        User user = createNewUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_NotOk() {
        User user = createNewUser(VALID_LOGIN, "", VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNullValue_NotOk() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAgeUser_NotOk() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeUserAge_NotOk() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_zeroAgeValue_NotOk() {
        User user = createNewUser(VALID_LOGIN, VALID_PASSWORD, ZERO_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    private User createNewUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
