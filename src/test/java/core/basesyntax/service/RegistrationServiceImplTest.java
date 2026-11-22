package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long DEFAULT_NEW_USER_ID = 2L;
    private static final String DEFAULT_LOGIN = "singleUser";
    private static final String ONE_LETTER_TOO_SHORT_STRING = "short";
    private static final String TOO_SHORT_STRING = "too";
    private static final String LONG_ENOUGH_STRING = "notshort";
    private static final String MINIMUM_LENGTH_LOGIN = "enough";
    private static final String DEFAULT_NEW_LOGIN = "newUser";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String EMPTY_STRING = "";
    private static final Integer DEFAULT_AGE = 19;
    private static final Integer UNDERAGE = 17;
    private static final Integer BORDERLINE_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private static User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User(DEFAULT_USER_ID, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE));
        testUser = new User(DEFAULT_NEW_USER_ID, DEFAULT_NEW_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
    }

    @Test
    void register_nullUser_throws() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_testUserData_ok() {
        User result = registrationService.register(testUser);
        assertNotNull(storageDao.get(result.getLogin()));
        assertEquals(testUser, result);
    }

    @Test
    void register_existingLogin_notOk() {
        testUser.setLogin(DEFAULT_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_minimumLengthLogin_ok() {
        testUser.setLogin(MINIMUM_LENGTH_LOGIN);
        User result = registrationService.register(testUser);
        assertNotNull(storageDao.get(result.getLogin()));
        assertEquals(testUser, result);
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginOneLetterTooShort_notOk() {
        testUser.setLogin(ONE_LETTER_TOO_SHORT_STRING);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginTooShort_notOk() {
        testUser.setLogin(TOO_SHORT_STRING);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginLongEnough_ok() {
        testUser.setLogin(LONG_ENOUGH_STRING);
        User result = registrationService.register(testUser);
        assertNotNull(storageDao.get(result.getLogin()));
        assertEquals(testUser, result);
    }

    @Test
    void register_loginEmpty_notOk() {
        testUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordTooShort_notOk() {
        testUser.setPassword(TOO_SHORT_STRING);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordEmpty_notOk() {
        testUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageTooLittle_notOk() {
        testUser.setAge(UNDERAGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageNegative_notOk() {
        testUser.setAge(-DEFAULT_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageIsEighteen_ok() {
        testUser.setAge(BORDERLINE_AGE);
        User result = registrationService.register(testUser);
        assertNotNull(storageDao.get(result.getLogin()));
        assertEquals(testUser, result);
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
