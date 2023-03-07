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
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(20);
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
        testUser.setLogin("bob");
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
        testUser.setLogin("ben");
        testUser.setAge(18);
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
    void register_passwordWithFiveCharacters_notOk() {
        testUser.setPassword("12345");

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithSixCharacters_ok() {
        testUser.setLogin("john");
        String expectedPassword = testUser.getPassword();

        registrationService.register(testUser);
        String actualPassword = storageDao.get(testUser.getLogin()).getPassword();

        assertEquals(expectedPassword, actualPassword, "Passwords for the user are different");
    }
}
