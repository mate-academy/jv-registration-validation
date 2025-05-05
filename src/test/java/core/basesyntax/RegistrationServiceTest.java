package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    public static final int MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD = 6;
    public static final int MINIMUM_USER_AGE_TO_LOGIN = 18;
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private static User user;
    private static Storage storage;

    @BeforeAll
    public static void initializeResources() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storage = new Storage();
    }

    @Test
    void userShouldBeNotNull() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void validLogin_OK() {
        user.setLogin("Vova_Vovin");
        int actual = user.getLogin().length();
        boolean expected = actual >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
        assertTrue(expected);
    }

    @Test
    void notValidLogin_notOK() {
        user.setLogin("Vova");
        int actual = user.getLogin().length();
        boolean expected = actual >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
        assertFalse(expected);
    }

    @Test
    void login_is_Null_notOK() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void validPassword_OK() {
        user.setPassword("Vova_Vovin");
        int actual = user.getPassword().length();
        boolean expected = actual >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
        assertTrue(expected);
    }

    @Test
    void notValidPassword_notOK() {
        user.setPassword("Vova1");
        int actual = user.getPassword().length();
        boolean expected = actual >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD;
        assertFalse(expected);
    }

    @Test
    void password_is_Null_notOK() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void validUserAge_OK() {
        user.setAge(18);
        int actual = user.getAge();
        boolean expected = actual >= MINIMUM_USER_AGE_TO_LOGIN;
        assertTrue(expected);
    }

    @Test
    void notValidUserAge_notOK() {
        user.setAge(17);
        int actual = user.getAge();
        boolean expected = actual >= MINIMUM_USER_AGE_TO_LOGIN;
        assertFalse(expected);
    }

    @Test
    void age_is_Null_notOK() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationWasOk() {
        user = new User();
        user.setAge(18);
        user.setLogin("1234567");
        user.setPassword("zxcqwe123");
        User actual;
        actual = registrationService.register(user);
        assertEquals(user, actual);

    }

    @Test
    void registrationWasNotOk() {
        User actual = new User();
        user = new User();
        user.setAge(18);
        user.setLogin("1234567");
        user.setPassword("qwert");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
