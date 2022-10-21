package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User VALID_USER = new User();
    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 17;
    private static final String VALID_LOGIN = "B";
    private static final String INVALID_LOGIN = "";
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_PASSWORD = "passw";
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        VALID_USER.setAge(VALID_AGE);
        VALID_USER.setLogin(VALID_LOGIN);
        VALID_USER.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(null));
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setAge(VALID_AGE);
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(userWithNullLogin));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setAge(VALID_AGE);
        userWithNullPassword.setLogin(VALID_LOGIN);
        userWithNullPassword.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(userWithNullPassword));
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setAge(null);
        userWithNullAge.setLogin(VALID_LOGIN);
        userWithNullAge.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(userWithNullAge));
    }

    @Test
    void register_validUser_ok() {
        String expected = REGISTRATION_SERVICE.register(VALID_USER).getLogin();
        String actual = STORAGE_DAO.get(VALID_USER.getLogin()).getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithWrongPassword_notOk() {
        User userWithWrongPassword = new User();
        userWithWrongPassword.setAge(VALID_AGE);
        userWithWrongPassword.setLogin(VALID_LOGIN);
        userWithWrongPassword.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(userWithWrongPassword));
    }

    @Test
    void register_userWithWrongAge_notOk() {
        User userWithWrongAge = new User();
        userWithWrongAge.setAge(INVALID_AGE);
        userWithWrongAge.setLogin(VALID_LOGIN);
        userWithWrongAge.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(userWithWrongAge));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        Storage.people.add(VALID_USER);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(VALID_USER));
    }

    @Test
    void register_userWithInvalidLogin_notOk() {
        User userWithWrongLogin = new User();
        userWithWrongLogin.setAge(VALID_AGE);
        userWithWrongLogin.setLogin(INVALID_LOGIN);
        userWithWrongLogin.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(userWithWrongLogin));
    }
}
