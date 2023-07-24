package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User INITIAL_USER_IN_STORAGE = new User("validLogin",
            "validPassword", 18);
    private static final int INITIAL_SIZE_OF_STORAGE = 1;
    private static final String VALID_LOGIN_EXAMPLE = "user20";
    private static final String VALID_PASSWORD_EXAMPLE = "password";
    private static final int MIN_VALID_AGE = 18;
    private static final String INVALID_LOGIN_EXAMPLE1 = "user1";
    private static final String INVALID_LOGIN_EXAMPLE2 = "";
    private static final String INVALID_LOGIN_EXAMPLE3 = "      ";
    private static final String INVALID_PASSWORD_EXAMPLE1 = "12345";
    private static final String INVALID_PASSWORD_EXAMPLE2 = "";
    private static final String INVALID_PASSWORD_EXAMPLE3 = "      ";
    private static final int INVALID_AGE_EXAMPLE = 15;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(INITIAL_USER_IN_STORAGE);
    }

    @Test
    void register_newUser_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(expected.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_alreadyExistedUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INITIAL_USER_IN_STORAGE);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_nullUser_notOkay() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithValidLogin_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(expected.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithInvalidLogin_notOk() {
        User fiveCharacterLoginUser = new User(INVALID_LOGIN_EXAMPLE1,
                VALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(fiveCharacterLoginUser);
        });

        User fourCharacterLoginUser = new User(INVALID_LOGIN_EXAMPLE2,
                VALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(fourCharacterLoginUser);
        });

        User oneCharacterLoginUser = new User(INVALID_LOGIN_EXAMPLE3,
                VALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(oneCharacterLoginUser);
        });

        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User nullLoginUser = new User(null, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithValidPassword_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        User actual = null;
        try {
            actual = registrationService.register(expected);
        } catch (RegistrationException e) {
            fail(e.getMessage());
        }
        assertEquals(expected, actual);
        int expectedSizeOfStorage = INITIAL_SIZE_OF_STORAGE + 1;
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(expectedSizeOfStorage, actualSizeOfStorage);
    }

    @Test
    void register_userWithInvalidPassword_notOk() {
        User fiveCharacterPasswordUser = new User(VALID_LOGIN_EXAMPLE,
                INVALID_PASSWORD_EXAMPLE1,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(fiveCharacterPasswordUser);
        });

        User fourCharacterPasswordUser = new User(VALID_LOGIN_EXAMPLE,
                INVALID_PASSWORD_EXAMPLE2,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(fourCharacterPasswordUser);
        });

        User oneCharacterPasswordUser = new User(VALID_LOGIN_EXAMPLE,
                INVALID_PASSWORD_EXAMPLE3,
                MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(oneCharacterPasswordUser);
        });

        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User nullPasswordUser = new User(VALID_LOGIN_EXAMPLE, null, MIN_VALID_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithValidAge_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(expected.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithLessThanMinAge_notOk() {
        User user = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, INVALID_AGE_EXAMPLE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        User user = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, -1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @Test
    void register_userWithNullAge_notOk() {
        User user = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        int actualSizeOfStorage = Storage.people.size();
        assertEquals(INITIAL_SIZE_OF_STORAGE, actualSizeOfStorage);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
