package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static final long DEFAULT_ID = 1111;
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static final Integer DEFAULT_AGE = 19;
    private static final String WRONG_PASSWORD = "1234";
    private static final String SHORTEST_PASSWORD = "123456";
    private static final Integer AGE_LESS_THAN_MIN = 17;
    private static final Integer NEGATIVE_AGE = -20;
    private static final Integer MIN_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void getDefaultUser() {
        user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_ageLessThanMin_notOk() {
        user.setAge(AGE_LESS_THAN_MIN);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsMin_Ok() {
        user.setAge(MIN_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordShorterThanMin_notOk() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortestPassword_ok() {
        user.setPassword(SHORTEST_PASSWORD);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_password_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        storageDao.add(user);
        User duplicateUser = new User();
        duplicateUser.setId(DEFAULT_ID);
        duplicateUser.setLogin(DEFAULT_LOGIN);
        duplicateUser.setPassword(DEFAULT_PASSWORD);
        duplicateUser.setAge(DEFAULT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(duplicateUser));
    }

    @Test
    void register_login_ok() {
        //A user is added to storage to check that no duplicate login is added.
        User userInStorage = new User();
        userInStorage.setId(DEFAULT_ID + 1);
        userInStorage.setLogin(DEFAULT_LOGIN + "1");
        userInStorage.setPassword(DEFAULT_PASSWORD + "1");
        userInStorage.setAge(DEFAULT_AGE + 1);
        storageDao.add(userInStorage);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
