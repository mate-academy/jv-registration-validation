package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user = new User();
    private static final String VALID_LOGIN = "validuser";
    private static final String ANOTHER_VALID_LOGIN = "alsovaliduser";
    private static final String INVALID_LOGIN_5CHARS = "user1";
    private static final String EMPTY_LOGIN = "";
    private static final String INVALID_LOGIN_NULL = null;
    private static final String VALID_PASSWORD = "validpassword";
    private static final String ANOTHER_VALID_PASSWORD = "anotherpassword";
    private static final String INVALID_PASSWORD_5CHARS = "passw";
    private static final String EMPTY_PASSWORD = "";
    private static final String INVALID_PASSWORD_NULL = null;
    private static final int VALID_AGE = 20;
    private static final int ANOTHER_VALID_AGE = 23;
    private static final int INVALID_AGE_EDGE = 17;
    private static final long VALID_ID = 176545647L;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setId(VALID_ID);
    }

    @Test
    void registerValidUser() throws RegistrationException {
        assertNotNull(registrationService.register(user));
    }

    @Test
    void registerDublicateUser_notOk() {
        Storage.people.add(user);
        User dublicatedUser = new User();
        dublicatedUser.setLogin(VALID_LOGIN);
        dublicatedUser.setPassword(ANOTHER_VALID_PASSWORD);
        dublicatedUser.setAge(VALID_AGE);
        dublicatedUser.setId(VALID_ID);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(dublicatedUser));
    }

    @Test
    void login5Chars_notOk() {
        user.setLogin(INVALID_LOGIN_5CHARS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyLogin_notOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void edgePassword_notOk() {
        user.setPassword(INVALID_PASSWORD_5CHARS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void youngAge_notOk() {
        user.setAge(INVALID_AGE_EDGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsNull_notOk() {
        user.setLogin(INVALID_LOGIN_NULL);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsNull_notOk() {
        user.setPassword(INVALID_PASSWORD_NULL);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void getNullFromStorage_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.get(null));
    }

    @Test
    void getUserFromStorage_ok() {
        Storage.people.add(user);
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void userEquals_ok() {
        User expected = user;
        User actual = user;
        assertEquals(expected, actual);
    }

    @Test
    void userNotEqualsIfLoginDifferent_ok() {
        User actual = new User();
        actual.setLogin(ANOTHER_VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(VALID_AGE);
        actual.setId(VALID_ID);
        User expected = user;
        assertNotEquals(expected, actual);
    }

    @Test
    void userNotEqualsIfPasswordDifferent_ok() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(ANOTHER_VALID_PASSWORD);
        actual.setAge(VALID_AGE);
        actual.setId(VALID_ID);
        User expected = user;
        assertNotEquals(expected, actual);
    }

    @Test
    void userNotEqualsIfAgeDifferent_ok() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(ANOTHER_VALID_AGE);
        actual.setId(VALID_ID);
        User expected = user;
        assertNotEquals(expected, actual);
    }

    @Test
    void userNotEqualsIfUserNull_ok() {
        User expected = user;
        User actual = null;
        assertNotEquals(expected, actual);
    }

    @Test
    void hashCodeEquals_ok() {
        User expected = user;
        User actual = user;
        assertEquals(expected.hashCode(), actual.hashCode());
    }

    @Test
    void hashCodeNotEquals_ok() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(ANOTHER_VALID_AGE);
        actual.setId(VALID_ID);
        User expected = user;
        assertNotEquals(expected.hashCode(), actual.hashCode());
    }

    @AfterEach
    void tearDown() {
        storageDao.clear();
    }
}
