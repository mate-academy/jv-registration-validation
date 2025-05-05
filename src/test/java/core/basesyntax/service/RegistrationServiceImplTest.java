package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegisterValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long FIRST_TEST_ID = 12431;
    private static final long SECOND_TEST_ID = 25978;

    private static final Integer AGE_18_YEARS = 18;
    private static final Integer AGE_LES_18_YEARS = 17;
    private static final Integer AGE_MORE_18_YEARS = 19;
    private static final Integer AGE_MORE_80_YEARS = 83;

    private static final String LOGIN_OK = "GrebanaRusnya";
    private static final String LOGIN_SECOND_OK = "PutinLoh";
    private static final String LOGIN_CONTAINS_SPACE = "Slava Ukraine";

    private static final String PASSWORD_8_SYMBOL_OK = "adEgrhXgh";
    private static final String PASSWORD_8_SYMBOL_SECOND_OK = "adEgdgfrhXgh";
    private static final String PASSWORD_8_SYMBOL_NOTOK = "y5monya";

    private static StorageDao storageDao;
    private static User testUser;
    private static User secondTestUser;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(FIRST_TEST_ID);
        testUser.setAge(AGE_18_YEARS);
        testUser.setLogin(LOGIN_OK);
        testUser.setPassword(PASSWORD_8_SYMBOL_OK);

        secondTestUser = new User();
        secondTestUser.setId(SECOND_TEST_ID);
        secondTestUser.setAge(AGE_MORE_18_YEARS);
        secondTestUser.setLogin(LOGIN_SECOND_OK);
        secondTestUser.setPassword(PASSWORD_8_SYMBOL_SECOND_OK);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_NotOk() {
        testUser = null;
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the null user, but it wasn't");
    }

    @Test
    void register_less18YearsAge_NotOk() {
        testUser.setAge(AGE_LES_18_YEARS);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the user age less then 18, but it wasn't");
    }

    @Test
    void register_nullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the null user age, but it wasn't");
    }

    @Test
    void register_more_Then80_Age_NotOk() {
        testUser.setAge(AGE_MORE_80_YEARS);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the user age more then 18, but it wasn't");
    }

    @Test
    void register_loginContainsSpace_NotOk() {
        testUser.setLogin(LOGIN_CONTAINS_SPACE);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the user login contain space, but it wasn't");
    }

    @Test
    void register_login_null_NotOk() {
        testUser.setLogin(null);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the null user login, but it wasn't");
    }

    @Test
    void register_password_null_NotOk() {
        testUser.setPassword(null);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown for the null user password, but it wasn't");
    }

    @Test
    void register_password_LessThen_8_NotOk() {
        testUser.setPassword(PASSWORD_8_SYMBOL_NOTOK);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown if password contain less then 8 characters, but it wasn't");
    }

    @Test
    void register_login_AlreadyExist_NotOk() {
        storageDao.add(testUser);
        testUser.setPassword(PASSWORD_8_SYMBOL_SECOND_OK);
        secondTestUser.setLogin(LOGIN_OK);
        assertThrows(RegisterValidationException.class, () -> {
            registrationService.register(secondTestUser);
        }, "Expected " + RegisterValidationException.class.getName()
                + " to be thrown if username already exists in storage, but it wasn't");
    }

    @Test
    void register_login_NotExist_Ok() {
        storageDao.add(testUser);
        testUser.setPassword(PASSWORD_8_SYMBOL_SECOND_OK);
        secondTestUser.setLogin(LOGIN_SECOND_OK);
        assertDoesNotThrow(() -> {
            registrationService.register(secondTestUser);
        }, "Expected: none Exception here, but it was");
    }

    @Test
    void register_compare_SaveUserAge_Ok() {
        registrationService.register(testUser);
        registrationService.register(secondTestUser);
        Integer expected = testUser.getAge();
        Integer actual = storageDao.get(testUser.getLogin()).getAge();
        String message = "Expected Age in storage  " + expected + " but was " + actual;
        assertEquals(expected, actual, message);
    }

    @Test
    void register_compare_SaveUserPassword_Ok() {
        registrationService.register(testUser);
        registrationService.register(secondTestUser);
        String expected = testUser.getPassword();
        String actual = storageDao.get(testUser.getLogin()).getPassword();
        String message = "Expected Password in storage  " + expected + " but was " + actual;
        assertEquals(expected, actual, message);
    }
}
