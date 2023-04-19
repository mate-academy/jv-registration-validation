package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegisterException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User FIRST_USER = new User("login1", "password", 18);
    private static final User SECOND_USER = new User("login113213", "password32 123321", 45);
    private static final User NULL_LOGIN_USER = new User(null, "password", 18);
    private static final User NULL_PASSWORD_USER = new User("login1", null, 18);
    private static final User NULL_AGE_USER = new User("login1", "password", null);
    private static final User UNDER_MIN_LOGIN_USER = new User("log", "password", 18);
    private static final User UNDER_MIN_PASSWORD_USER = new User("login1", "pass", 18);
    private static final User UNDER_MIN_AGE_USER = new User("login1", "password", 10);
    private static final User UNDER_MIN_VALUES_USER = new User("login", "123", 16);
    private static final User ZERO_VALUES_USER = new User("", "", 0);
    private static final User NULL_VALUES_USER = new User(null, null, null);
    private static final int EXPECTED_SIZE = 2;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private Storage storage;

    @BeforeAll
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void register_nullAge_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(NULL_AGE_USER);
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }

    @Test
    public void register_addUser_ok() {
        assertThrows(Exception.class, () -> {
            registrationService.register(FIRST_USER);
        });
    }

    @Test
    public void register_addUserAndCheck_ok() {
        try {
            registrationService.register(FIRST_USER);
            User actual = Storage.people.get(0);
            assertEquals(FIRST_USER, actual);
        } catch (RegisterException e) {
            fail();
        }

    }

    @Test
    public void register_notAllowedUser_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(UNDER_MIN_VALUES_USER);
        });
    }

    @Test
    public void register_notAllowedLogin_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(UNDER_MIN_LOGIN_USER);
        });
    }

    @Test
    public void register_notAllowedPassword_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(UNDER_MIN_PASSWORD_USER);
        });
    }

    @Test
    public void register_notAllowedAge_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(UNDER_MIN_AGE_USER);
        });
    }

    @Test
    public void register_zeroValues_notOk() {
        assertThrows(Exception.class, () -> {
            registrationService.register(ZERO_VALUES_USER);
        });
    }

    @Test
    public void userAdd_addingUser_ok() {
        storageDao.add(FIRST_USER);
        storageDao.add(SECOND_USER);;
        assertTrue(Storage.people.contains(FIRST_USER));
        assertTrue(Storage.people.contains(SECOND_USER));
    }

    @Test
    public void userGet_checkFirstUser_ok() {
        Storage.people.add(FIRST_USER);
        User actual = storageDao.get(FIRST_USER.getLogin());
        assertEquals(FIRST_USER, actual);
    }

    @Test
    public void userGet_checkNullValue_notOk() {
        Storage.people.add(NULL_VALUES_USER);
        assertThrows(Exception.class, () -> {
            storageDao.get(NULL_VALUES_USER.getLogin());
        });
    }
}
