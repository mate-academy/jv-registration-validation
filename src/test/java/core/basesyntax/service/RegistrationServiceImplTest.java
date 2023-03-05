package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.validators.RegistrationValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_LESS_MIN = 17;
    private static final int AGE_LESS_ZERO = -10;
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;
    private User testUser;

    @BeforeEach
    void init() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUser = new User();
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
    void register_loginStartWithSpace_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin(" test");
        testUser.setPassword("123456");
        testUser.setAge(20);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_loginLessThanFiveCharacters_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("test");
        testUser.setPassword("123456");
        testUser.setAge(20);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_existingUserInStorage_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(20);
        storageDao.add(testUser);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_addNewUserInStorage_ok() throws RegistrationValidatorException {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(20);
        User expctedUser = testUser;
        User actualUser = registrationService.register(testUser);

        assertEquals(expctedUser, actualUser, "User has not been added to the storage");
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(null);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(AGE_LESS_MIN);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageLessZero_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(AGE_LESS_ZERO);
        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_age18_ok() throws RegistrationValidatorException {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(18);
        Integer expectedAge = testUser.getAge();
        Integer actualAge = registrationService.register(testUser).getAge();

        assertEquals(expectedAge, actualAge, "Added user has incorrect age");
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword(null);
        testUser.setAge(20);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithFiveCharacters_notOk() {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("12345");
        testUser.setAge(20);

        assertThrows(RegistrationValidatorException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordWithSixCharacters_ok() throws RegistrationValidatorException {
        testUser.setId(Long.valueOf("1"));
        testUser.setLogin("nicolas");
        testUser.setPassword("123456");
        testUser.setAge(20);
        String expectedPassword = testUser.getPassword();

        registrationService.register(testUser);
        String actualPassword = storageDao.get(testUser.getLogin()).getPassword();

        assertEquals(expectedPassword, actualPassword, "Passwords for the user are different");
    }
}
