package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User NULL_USER = null;
    private static final String VALID_LOGIN = "tazbog123";
    private static final String LENGTH_FOUR_INVALID_LOGIN = "1234";
    private static final String LENGTH_EMPTY_INVALID_LOGIN = "";
    private static final String LENGTH_SIX_VALID_LOGIN = "123456";
    private static final String NULL_LOGIN = null;
    private static final String VALID_PASSWORD = "tazbog123";
    private static final String LENGTH_FOUR_INVALID_PASSWORD = "abcd";
    private static final String LENGTH_EMPTY_INVALID_PASSWORD = "";
    private static final String LENGTH_SIX_VALID_PASSWORD = "abcdef";
    private static final String NULL_PASSWORD = null;
    private static final Integer VALID_AGE = 25;
    private static final Integer YOUNG_INVALID_AGE = 17;
    private static final Integer NEGATIVE_INVALID_AGE = -59;
    private static final Integer MIN_VALID_AGE = 18;
    private static final Integer NULL_AGE = null;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_Ok() {
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginDuplicate_notOk() {
        storageDao.add(user);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(NULL_USER));
    }

    @Test
    void register_userAge_notOk() {
        user.setAge(YOUNG_INVALID_AGE);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(NULL_AGE);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNegative_notOk() {
        user.setAge(NEGATIVE_INVALID_AGE);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userMinAge_Ok() {
        user.setAge(MIN_VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidAge_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(NULL_LOGIN);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLengthFour_notOk() {
        user.setLogin(LENGTH_FOUR_INVALID_LOGIN);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userEmptyLogin_notOk() {
        user.setLogin(LENGTH_EMPTY_INVALID_LOGIN);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLengthSix_Ok() {
        user.setLogin(LENGTH_SIX_VALID_LOGIN);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidLogin_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userEmptyPassword_notOk() {
        user.setPassword(LENGTH_EMPTY_INVALID_PASSWORD);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordNull_notOk() {
        user.setPassword(NULL_PASSWORD);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLengthFour_notOk() {
        user.setPassword(LENGTH_FOUR_INVALID_PASSWORD);
        assertThrows(InvalidValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLengthSix_Ok() {
        user.setPassword(LENGTH_SIX_VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidPassword_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
