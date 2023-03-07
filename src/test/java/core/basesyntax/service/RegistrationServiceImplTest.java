package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationValidatorException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_LESS_MIN = 17;
    private static final int AGE_LESS_ZERO = -10;
    private static final String DEFAULT_LOGIN = "nicolas";
    private static final String LOGIN_FOR_NEW_USER = "bob";
    private static final String LOGIN_FOR_CORRECT_AGE = "ben";
    private static final String LOGIN_WITH_CORRECT_PASSWORD = "john";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String PASSWORD_LESS_MIN_BORDER = "12345";
    private static final int DEFAULT_AGE = 20;
    private static final User EMPTY_USER = null;
    private static final String NULL_STRING = null;
    private static final Integer NULL_AGE = null;
    private static final Integer MIN_BORDER_AGE = 18;
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void init() {
        testUser = new User();
        testUser.setLogin(DEFAULT_LOGIN);
        testUser.setPassword(DEFAULT_PASSWORD);
        testUser.setAge(DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(EMPTY_USER);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(NULL_STRING);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_existingUserInStorage_notOk() {
        storageDao.add(testUser);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_addNewUserInStorage_ok() {
        testUser.setLogin(LOGIN_FOR_NEW_USER);
        User expectedUser = testUser;
        User actualUser = registrationService.register(testUser);

        assertEquals(expectedUser, actualUser, "User has not been added to the storage");
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(NULL_AGE);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        testUser.setAge(AGE_LESS_MIN);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageLessZero_notOk() {
        testUser.setAge(AGE_LESS_ZERO);
        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_age18_ok() {
        testUser.setLogin(LOGIN_FOR_CORRECT_AGE);
        testUser.setAge(MIN_BORDER_AGE);
        Integer expectedAge = testUser.getAge();
        Integer actualAge = registrationService.register(testUser).getAge();

        assertEquals(expectedAge, actualAge, "Added user has incorrect age");
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(NULL_STRING);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithFiveCharacters_notOk() {
        testUser.setPassword(PASSWORD_LESS_MIN_BORDER);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithSixCharacters_ok() {
        testUser.setLogin(LOGIN_WITH_CORRECT_PASSWORD);
        String expectedPassword = testUser.getPassword();

        registrationService.register(testUser);
        String actualPassword = storageDao.get(testUser.getLogin()).getPassword();

        assertEquals(expectedPassword, actualPassword, "Passwords for the user are different");
    }
}
