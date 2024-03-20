package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MINIMAL_USER_AGE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "someLogin";
    public static final String DEFAULT_PASSWORD = "somePassword";

    private static StorageDao storageDao;
    private static RegistrationService service;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(storageDao);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_notNull_ok() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MINIMAL_USER_AGE);
        int storageSize = Storage.people.size();
        assertDoesNotThrow(() -> service.register(user),
                "Exception while registering user " + user);
        assertEquals(storageSize + 1, Storage.people.size(),
                "Storage size is " + Storage.people.size() + " but expected" + storageSize + 1);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, DEFAULT_PASSWORD, MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_tooShortLogin_notOk() {
        User user1 = new User("lgn28", DEFAULT_PASSWORD, MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user1));
        User user2 = new User("login", DEFAULT_PASSWORD, MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user2));
    }

    @Test
    void register_loginRepeated_notOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MINIMAL_USER_AGE);
        service.register(user);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_login_ok() {
        User actual;
        User expected;
        int storageSize;
        String[] loginValues = new String[]{"login1", "loginX", "123456", "Nagibator_228"};

        for (String login: loginValues) {
            storageSize = Storage.people.size();
            actual = new User(login, DEFAULT_PASSWORD, MINIMAL_USER_AGE);
            expected = service.register(actual);
            assertEquals(actual, expected,
                    "User with login " + login + " not returned correctly");
            assertEquals(storageDao.get(actual.getLogin()), actual,
                    "User with login " + login + " not registered correctly");
            assertEquals(storageSize + 1, Storage.people.size(),
                    "Storage size is " + Storage.people.size() + " but expected" + storageSize + 1);
        }
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(DEFAULT_LOGIN,null, MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_tooShortPassword_notOk() {
        User user1 = new User(DEFAULT_LOGIN + 1, "pass1", MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user1));
        User user2 = new User(DEFAULT_LOGIN + 2, "pswrd", MINIMAL_USER_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user2));
    }

    @Test
    void register_password_ok() {
        User actual;
        User expected;
        int storageSize;
        long counter = 0;
        String[] passwordValues = new String[]
                {"password", "12345678", "xXx_xXx123456789", "jkdH_das23$^c5hj"};

        for (String password: passwordValues) {
            storageSize = Storage.people.size();
            actual = new User(DEFAULT_LOGIN + counter++, password, MINIMAL_USER_AGE);
            expected = service.register(actual);
            assertEquals(actual, expected,
                    "User with password " + password + " not returned correctly");
            assertEquals(storageDao.get(actual.getLogin()), actual,
                    "User with password " + password + " not registered correctly");
            assertEquals(storageSize + 1, Storage.people.size(),
                    "Storage size is " + Storage.people.size() + " but expected" + storageSize + 1);
        }
    }

    @Test
    void register_invalidAge_notOk() {
        long counter = 0;
        int[] ageValues = new int[]{-1, 0, MINIMAL_USER_AGE - 5, MINIMAL_USER_AGE - 1};

        for (int age: ageValues) {
            User user = new User(DEFAULT_LOGIN + counter, DEFAULT_PASSWORD, age);
            assertThrows(RegistrationException.class, () -> service.register(user),
                    "User with age " + age + " can't be registered");
        }
    }

    @Test
    void register_age_ok() {
        User actual;
        User expected;
        int storageSize;
        long counter = 0;
        int[] ageValues = new int[]{MINIMAL_USER_AGE, MINIMAL_USER_AGE + 1,
                MINIMAL_USER_AGE + 5, MINIMAL_USER_AGE + 50};

        for (int age : ageValues) {
            storageSize = Storage.people.size();
            actual = new User(DEFAULT_LOGIN + counter++, DEFAULT_PASSWORD, age);
            expected = service.register(actual);
            assertEquals(actual, expected,
                    "User with age " + age + " not returned correctly");
            assertEquals(storageDao.get(actual.getLogin()), actual,
                    "User with age " + age + " not registered correctly");
            assertEquals(storageSize + 1, Storage.people.size(),
                    "Storage size is " + Storage.people.size() + " but expected" + storageSize + 1);
        }
    }
}
