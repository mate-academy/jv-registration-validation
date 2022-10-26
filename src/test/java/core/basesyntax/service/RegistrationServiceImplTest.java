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
    private static final String bobLogin = "Bob";
    private static final String johnLogin = "John";
    private static final String aliceLogin = "Alice";
    private static final String validPassword = "4567891";
    private static final String shortPassword = "45678";
    private static final int validAge = 19;
    private static final int thresholdAge = 18;
    private static final int nonValidAge = 17;
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
        user.setAge(validAge);
        user.setLogin(bobLogin);
        user.setPassword(validPassword);
    }

    @Test
    void registerValidUser_Ok() {
        User actual = storageDao.add(user);
        assertEquals(user, actual);
    }

    @Test
    void lessThanMinAge_NotOk() {
        user.setAge(nonValidAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userIsNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void alreadyRegister_NotOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNormalLogin_ok() {
        user.setLogin(aliceLogin);
        User actual = storageDao.add(user);
        assertEquals(user, actual);
    }

    @Test
    void userWithMinAge_Ok() {
        user.setLogin(johnLogin);
        user.setAge(thresholdAge);
        User actual = storageDao.add(user);
        assertEquals(user, actual);
    }

    @Test
    void tooShortPass_NotOk() {
        user.setPassword(shortPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void negativeAgeUser_NotOk() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
