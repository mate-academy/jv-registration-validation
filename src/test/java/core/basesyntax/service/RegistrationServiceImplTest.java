package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static User invalidUser;

    @BeforeAll
    static void setUpAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(0L);
        validUser.setAge(20);
        validUser.setLogin("validLogin");
        validUser.setPassword("validPassword");
        invalidUser = new User();
        invalidUser.setId(0L);
        invalidUser.setAge(20);
        invalidUser.setLogin("validLogin");
        invalidUser.setPassword("validPassword");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        },"register(user) with user == null must throw RuntimeException.");
    }

    @Test
    void register_userAgeNull_notOk() {
        invalidUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Age == null must throw RuntimeException.");
    }

    @Test
    void register_userAgeInvalidExceptionMessageAboutAge_ok() {
        invalidUser.setAge(0);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with invalid age must throw RuntimeException.");
        boolean exceptionMessageContainsAge = exception.getMessage().toLowerCase().contains("age");
        assertTrue(exceptionMessageContainsAge, "register(user) with invalid age must throw "
                + "RuntimeException with message, contains \"age\".");
    }

    @Test
    void register_userAgeLessThenMinAge_notOk() {
        invalidUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with  age less than 18 must throw RuntimeException.");
    }

    @Test
    void register_userAgeNegative_notOk() {
        invalidUser.setAge(-30);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with negative age should must RuntimeException.");
    }

    @Test
    void register_userAgeValidAtMinAge_ok() {
        validUser.setLogin("validUserForAgeValidAtMinTest");
        validUser.setAge(18);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains valid user with Age>=18 after it registration.");
    }

    @Test
    void register_userWithValidAge_ok() {
        validUser.setLogin("validUserWithValidAgeTest");
        validUser.setAge(25);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains valid user with Age>=18 after it registration.");
    }

    @Test
    void register_userAgeIntegerMaxValue_ok() {
        validUser.setLogin("validUserForAgeIntegerMaxValueTest");
        validUser.setAge(Integer.MAX_VALUE);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid User "
                + "with Age == Integer.MAX_VALUE after it registration.");
    }

    @Test
    void register_userPasswordNull_notOk() {
        invalidUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password == null must throw RuntimeException.");
    }

    @Test
    void register_userPasswordInvalidExceptionMessageAboutPassword_ok() {
        invalidUser.setPassword("0");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with invalid password must throw RuntimeException.");
        boolean exceptionMessageContainsPassword
                = exception.getMessage().toLowerCase().contains("password");
        assertTrue(exceptionMessageContainsPassword,
                "register(user) with invalid password must throw RuntimeException "
                + "with message, contains \"password\".");
    }

    @Test
    void register_userPasswordEmpty_notOk() {
        invalidUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Password must throw RuntimeException.");
    }

    @Test
    void register_userPasswordLengthLessMin_notOk() {
        invalidUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password.length() < 6 must throw RuntimeException.");
    }

    @Test
    void register_userPasswordLengthAtMinimum_ok() {
        validUser.setLogin("validUserForPasswordLengthAtMinimumTest");
        validUser.setPassword("a2c4e6");
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Password.length() >= 6 and <= 65535 after it registration.");
    }

    @Test
    void register_userPasswordVeryLong_ok() {
        validUser.setLogin("validUserForPasswordVeryLongTest");
        String symbol = "a";
        String passwordVeryLong = symbol.repeat(1 << 16);
        validUser.setPassword(passwordVeryLong);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Password.length() >= 6 and <= 65535 after it registration.");
    }

    @Test
    void register_userLoginNull_notOk() {
        invalidUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Login == null must throw RuntimeException.");
    }

    @Test
    void register_userLoginEmpty_notOk() {
        invalidUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Login must throw RuntimeException.");
    }

    @Test
    void register_userLoginExistingThrowExceptionMessageAboutLogin_ok() {
        validUser.setLogin("validUserForLoginMessageTest");
        registrationService.register(validUser);
        invalidUser.setLogin(validUser.getLogin());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with existing login must throw RuntimeException.");
        boolean exceptionMessageContainsLogin
                = exception.getMessage().toLowerCase().contains("login");
        assertTrue(exceptionMessageContainsLogin,
                "register(user) with existing login must throw RuntimeException "
                + "with message, contains \"login\".");
    }

    @Test
    void register_userLoginVeryLong_ok() {
        String symbol = "a";
        String loginVeryLong = symbol.repeat(1 << 16);
        validUser.setLogin(loginVeryLong);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Login.length() <= 65535 after it registration.");
    }

    @Test
    void register_validUserNotThrowRuntimeException_ok() {
        validUser.setLogin("validUserForNotThrowExceptionTest");
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) must NOT throw RuntimeException.");
        }
    }

    @Test
    void register_validUserAddedToStorage_ok() {
        validUser.setLogin("validUserForAddedToStorageTest");
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains validUser after it registration.");
    }

    @Test
    void register_validUserAddedToStorageIncrementStorageSize_ok() {
        validUser.setLogin("validUserForAddedToStorageIncrementStorageSize");
        int expectedSize = Storage.people.size() + 1;
        registrationService.register(validUser);
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize,
                "Storage.people.size() must be incremented after registration validUser.");
    }

    @Test
    void register_invalidUserAddedToStorage_notOk() {
        invalidUser.setLogin("invalidUserForAddedToStorageTest");
        invalidUser.setAge(1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(invalidUser) must throw RuntimeException.");
        assertFalse(Storage.people.contains(invalidUser),
                "Storage.people must NOT contains invalidUser after try it registration.");
    }

    @Test
    void register_invalidUserAddedToStorageIncrementStorageSize_notOk() {
        invalidUser.setLogin("invalidUserForAddedToStorageIncrementStorageSizeTest");
        invalidUser.setAge(2);
        int expectedSize = Storage.people.size();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(invalidUser) must throw RuntimeException.");
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize,
                "Storage.people.size() must be NOT changed "
                        + "after try registration invalidUser.");
    }
}
