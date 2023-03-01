package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationValidationExeption;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long FIRST_TEST_ID = 12345;
    private static final long SECOND_TEST_ID = 54321;
    private static final int AGE_18_YEARS = 18;
    private static final int AGE_LES_18_YEARS = 15;
    private static final int AGE_MORE_18_YEARS = 21;
    private static final int AGE_MORE_100_YEARS = 105;
    private static final String FIRST_LOGIN_OK = "TuttiFrutti";
    private static final String SECOND_LOGIN_OK = "FruttiTutti";
    private static final String LOGIN_CONTAINS_SPACE = "Gringo gringo";
    private static final String PASSWORD_8_SYMBOL_OK = "buffer789";
    private static final String PASSWORD_8_SYMBOL_SECOND_OK = "grinGo666";
    private static final String PASSWORD_LESS_THEN_8_OK = "notwork";
    private static StorageDao storageDao;
    private static User firstUser;
    private static User secondUser;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setId(FIRST_TEST_ID);
        firstUser.setLogin(FIRST_LOGIN_OK);
        firstUser.setAge(AGE_MORE_18_YEARS);
        firstUser.setPassword(PASSWORD_8_SYMBOL_OK);

        secondUser = new User();
        secondUser.setId(SECOND_TEST_ID);
        secondUser.setLogin(SECOND_LOGIN_OK);
        secondUser.setAge(AGE_18_YEARS);
        secondUser.setPassword(PASSWORD_8_SYMBOL_SECOND_OK);
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_NotOk() {
        firstUser = null;
        assertThrows(RegistrationValidationExeption.class,
                () -> registrationService.register(firstUser),
                "You can't register user with null name");
    }

    @Test
    void register_lessThen18yearsOld_NotOk() {
        firstUser.setAge(AGE_LES_18_YEARS);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "User must be older then 18");
    }

    @Test
    void register_NullAge_NotOk() {
        firstUser.setAge(null);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Please write your age");
    }

    @Test
    void register_NegativeAge_NotOk() {
        firstUser.setAge(-15);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Age less then 0");
    }

    @Test
    void register_moreThen100yearsOld() {
        firstUser.setAge(AGE_MORE_100_YEARS);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Age more then 100");
    }

    @Test
    void register_NullLogin_NotOk() {
        firstUser.setLogin(null);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Please write login");
    }

    @Test
    void register_loginWithSpace_NotOk() {
        firstUser.setLogin(LOGIN_CONTAINS_SPACE);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Your login contain space, please make login without space");
    }

    @Test
    void register_passwordNull_NotOK() {
        firstUser.setPassword(null);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Please write password");
    }

    @Test
    void register_passwordLessThen8_NotOk() {
        firstUser.setPassword(PASSWORD_LESS_THEN_8_OK);
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(firstUser);
        }, "Your password must be 8 symbols or bigger");
    }

    @Test
    void register_loginAlreadyExist_NotOk() {
        storageDao.add(firstUser);
        secondUser.setLogin(firstUser.getLogin());
        assertThrows(RegistrationValidationExeption.class, () -> {
            registrationService.register(secondUser);
        }, "User with this login already exist");
    }

    @Test
    void register_UserCanNotRegister_Ok() {
        firstUser.setAge(AGE_MORE_100_YEARS);
        firstUser.setLogin(LOGIN_CONTAINS_SPACE);
        firstUser.setPassword(PASSWORD_LESS_THEN_8_OK);
        assertThrows(RegistrationValidationExeption.class,
                () -> registrationService.register(firstUser));
    }

    @Test
    void register_loginNotExist_Ok() {
        storageDao.add(firstUser);
        assertDoesNotThrow(() -> {
            registrationService.register(secondUser);
        }, "User was added without problems");
    }

    @Test
    void register_compare_SaveUserAge_Ok() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        int expected = firstUser.getAge();
        int actual = storageDao.get(firstUser.getLogin()).getAge();
        String message = "Expected Age in storage (" + expected + ") but was (" + actual + ")";
        assertEquals(expected, actual, message);
    }

    @Test
    void register_compare_SaveUserPassword_Ok() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        String expected = firstUser.getPassword();
        String actual = storageDao.get(firstUser.getLogin()).getPassword();
        String message = "Expected Password in storage (" + expected + ") but was (" + actual + ")";
        assertEquals(expected, actual, message);
    }
}
