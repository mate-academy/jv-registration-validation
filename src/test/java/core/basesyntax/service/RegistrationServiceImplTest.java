package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    private static final int MIN_AGE = 18;
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_differentUsers_Ok() {
        User userFirst = createUser(DEFAULT_AGE, DEFAULT_PASSWORD, DEFAULT_LOGIN);
        User userSecond = createUser(DEFAULT_AGE + 1, DEFAULT_PASSWORD + "1", DEFAULT_LOGIN + "a");
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User expectedUser1 = storageDao.get(DEFAULT_LOGIN);
        User expectedUser2 = storageDao.get(DEFAULT_LOGIN + "a");
        assertNotEquals(expectedUser1, expectedUser2);
    }

    @Test
    void register_userIsNull_notOk() {
        checkException(null);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        checkException(user);
    }

    @Test
    void register_minAge_Ok() {
        user.setAge(MIN_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_moreThenMinAge_ok() {
        user.setAge(MIN_AGE + 1);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_lessThenMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        checkException(user);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        checkException(user);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        checkException(user);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        checkException(user);
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        checkException(user);
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("passw");
        checkException(user);
    }

    @Test
    void register_minPasswordLength_ok() {
        user.setPassword("passwo");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_moreThenMinPasswordLength_ok() {
        user.setPassword(DEFAULT_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User createUser(Integer age, String password, String login) {
        User user = new User();
        user.setAge(age);
        user.setPassword(password);
        user.setLogin(login);
        return user;
    }

    private void checkException(User currentUser) {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(currentUser));
    }
}
