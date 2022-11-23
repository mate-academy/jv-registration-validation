package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String DEFAULT_LOGIN = "Login";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final Integer DEFAULT_AGE = 18;
    private static final String INVALID_PASSWORD = "12345";
    private static final Integer YOUNGER_AGE = 17;
    private static final Integer OLDER_AGE = 50;
    private static final Integer NEGATIVE_AGE = -15;
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
        Storage.people.clear();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notAdultUser_notOk() {
        user.setAge(YOUNGER_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAgeOfUser_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void add_validUser_Ok() {
        assertEquals(user, storageDao.add(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullLogin_Ok() {
        user.setLogin(null);
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_adultUser_Ok() {
        user.setAge(OLDER_AGE);
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }
}
