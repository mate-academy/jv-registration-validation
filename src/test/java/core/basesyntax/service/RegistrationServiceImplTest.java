package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static User INITIAL_USER_IN_STORAGE;
    private static final String VALID_LOGIN_EXAMPLE = "user20";
    private static final String VALID_PASSWORD_EXAMPLE = "password";
    private static final int MIN_VALID_AGE = 18;
    private static final String INVALID_LOGIN_EXAMPLE = "user1";
    private static final String INVALID_LOGIN_EXAMPLE3_BLANK = "      ";
    private static final String INVALID_PASSWORD_EXAMPLE = "12345";
    private static final String INVALID_PASSWORD_EXAMPLE_BLANK = "      ";
    private static final int INVALID_AGE_EXAMPLE = 15;
    private static final String NULL_USER_ERROR_MESSAGE = "User can't be null!";
    private static final String EXISTING_USER_ERROR_MESSAGE = "This user is already "
                                                            + "existed in the Storage!";
    private static final String NULL_LOGIN_ERROR_MESSAGE = "User's login can't be null!";
    private static final String BLANK_LOGIN_ERROR_MESSAGE = "User's login can't consist "
                                                            + "only white spaces!";
    private static final String SHORT_LOGIN_ERROR_MESSAGE = "User's login should be "
                                                            + "longer than 6!";
    private static final String NULL_PASSWORD_ERROR_MESSAGE = "User's password can't be null!";
    private static final String BLANK_PASSWORD_ERROR_MESSAGE = "User's password can't consist "
                                                            + "only white spaces";
    private static final String SHORT_PASSWORD_ERROR_MESSAGE = "User's password should be "
                                                            + "longer than 6!";
    private static final String NULL_AGE_ERROR_MESSAGE = "User's age can't be null!";
    private static final String INVALID_AGE_ERROR_MESSAGE = "User can't be registered with "
                                                            + "less age than min: 18!";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        INITIAL_USER_IN_STORAGE = new User("validLogin", "validPassword", 18);
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(INITIAL_USER_IN_STORAGE);
    }

    @Test
    void register_newUser_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(VALID_LOGIN_EXAMPLE);
        assertEquals(expected, actual);
    }

    @Test
    void register_alreadyExistedUser_notOk() {
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(INITIAL_USER_IN_STORAGE);
        });
        assertEquals(EXISTING_USER_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_nullUser_notOkay() {
        User nullUser = null;
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
        assertEquals(NULL_USER_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithValidLogin_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(VALID_LOGIN_EXAMPLE);
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithInvalidLogin_notOk() {
        User invalidLoginUser = new User(INVALID_LOGIN_EXAMPLE,
                VALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidLoginUser);
        });
        assertEquals(SHORT_LOGIN_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithBlankLogin_notOk() {
        User blankLoginUser = new User(INVALID_LOGIN_EXAMPLE3_BLANK,
                VALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(blankLoginUser);
        });
        assertEquals(BLANK_LOGIN_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User nullLoginUser = new User(null, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        });
        assertEquals(NULL_LOGIN_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithValidPassword_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(VALID_LOGIN_EXAMPLE);
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithInvalidPassword_notOk() {
        User invalidPasswordUser = new User(VALID_LOGIN_EXAMPLE,
                INVALID_PASSWORD_EXAMPLE,
                MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(invalidPasswordUser);
        });
        assertEquals(SHORT_PASSWORD_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithBlankPassword_notOk() {
        User blankPasswordUser = new User(VALID_LOGIN_EXAMPLE,
                INVALID_PASSWORD_EXAMPLE_BLANK,
                MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(blankPasswordUser);
        });
        assertEquals(BLANK_PASSWORD_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User nullPasswordUser = new User(VALID_LOGIN_EXAMPLE, null, MIN_VALID_AGE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
        assertEquals(NULL_PASSWORD_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithValidAge_ok() {
        User expected = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, MIN_VALID_AGE);
        registrationService.register(expected);
        User actual = storageDao.get(VALID_LOGIN_EXAMPLE);
        assertEquals(expected, actual);
    }

    @Test
    void register_userWithLessThanMinAge_notOk() {
        User user = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, INVALID_AGE_EXAMPLE);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(INVALID_AGE_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void register_userWithNullAge_notOk() {
        User user = new User(VALID_LOGIN_EXAMPLE, VALID_PASSWORD_EXAMPLE, null);
        RegistrationException thrown = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(NULL_AGE_ERROR_MESSAGE, thrown.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
