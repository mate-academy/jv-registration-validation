package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationValidatorException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_LESS_MIN = 17;
    private static final int AGE_LESS_ZERO = -10;
    private static final String DEFAULT_LOGIN = "nicolas";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String PASSWORD_LESS_MIN_BORDER = "12345";
    private static final int DEFAULT_AGE = 20;
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

    @AfterEach
    void cleanUpEach() {
        Storage.people.remove(testUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);

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
        User expectedUser = testUser;
        User actualUser = registrationService.register(testUser);

        assertEquals(expectedUser, actualUser, "User has not been added to the storage");
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageLessThanBorderValue_notOk() {
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
    void register_ageWithBorderValue_ok() {
        testUser.setAge(MIN_BORDER_AGE);
        Integer expectedAge = testUser.getAge();
        Integer actualAge = registrationService.register(testUser).getAge();

        assertEquals(expectedAge, actualAge, "Added user has incorrect age");
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordLessThanBorderValue_notOk() {
        testUser.setPassword(PASSWORD_LESS_MIN_BORDER);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithBorderValue_ok() {
        String expectedPassword = testUser.getPassword();

        registrationService.register(testUser);
        String actualPassword = storageDao.get(testUser.getLogin()).getPassword();

        assertEquals(expectedPassword, actualPassword, "Passwords for the user are different");
    }
}
