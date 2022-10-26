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
    void register_userAgeNull_NotOk() {
        invalidUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Age == null must throw RuntimeException.");
    }

    @Test
    void register_userAgeInvalidExceptionMessageAboutAge_Ok() {
        invalidUser.setAge(0);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with invalid age must throw RuntimeException.");
        boolean exceptionMessageContainsAge = exception.getMessage().toLowerCase().contains("age");
        assertTrue(exceptionMessageContainsAge, "register(user) with invalid age must throw "
                + "RuntimeException with message, contains \"age\".");
    }

    @Test
    void register_userAgeFrom0toLeast_NotOk() {
        invalidUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Age 0 < 18 must throw RuntimeException.");
    }

    @Test
    void register_userAgeNegative_NotOk() {
        invalidUser.setAge(-30);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with negative age should must RuntimeException.");
    }

    @Test
    void register_userAgeAtLeast_Ok() {
        validUser.setLogin("validUserForAgeAtLeastTest");
        validUser.setAge(18);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains valid user with Age>=18 after it registration.");
    }

    @Test
    void register_userAgeAboveLeast_Ok() {
        validUser.setLogin("validUserForAgeAboveLeastTest");
        validUser.setAge(25);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains valid user with Age>=18 after it registration.");
    }

    @Test
    void register_userAgeIntegerMaxValue_Ok() {
        validUser.setLogin("validUserForAgeIntegerMaxValueTest");
        validUser.setAge(Integer.MAX_VALUE);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid User "
                + "with Age == Integer.MAX_VALUE after it registration.");
    }

    @Test
    void register_userPasswordNull_NotOk() {
        invalidUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password == null must throw RuntimeException.");
    }

    @Test
    void register_userPasswordInvalidExceptionMessageAboutPassword_Ok() {
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
    void register_userPasswordEmpty_NotOk() {
        invalidUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Password must throw RuntimeException.");
    }

    @Test
    void register_userPasswordLengthLessMin_NotOk() {
        invalidUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password.length() < 6 must throw RuntimeException.");
    }

    @Test
    void register_userPasswordLengthAtLeass_Ok() {
        validUser.setLogin("validUserForPasswordLengthAtLeassTest");
        validUser.setPassword("a2c4e6");
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Password.length() >= 6 and <= 65535 after it registration.");
    }

    @Test
    void register_userPasswordVeryLong_Ok() {
        validUser.setLogin("validUserForPasswordVeryLongTest");
        String symbol = "a";
        String passwordVeryLong = symbol.repeat(1 << 16);
        validUser.setPassword(passwordVeryLong);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Password.length() >= 6 and <= 65535 after it registration.");
    }

    @Test
    void register_userLoginNull_NotOk() {
        invalidUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Login == null must throw RuntimeException.");
    }

    @Test
    void register_userLoginEmpty_NotOk() {
        invalidUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Login must throw RuntimeException.");
    }

    @Test
    void register_userLoginExistingExceptionMessageAboutLogin_Ok() {
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
    void register_userLoginVeryLong_Ok() {
        String symbol = "a";
        String loginVeryLong = symbol.repeat(1 << 16);
        validUser.setLogin(loginVeryLong);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser), "Storage.people must contains valid user "
                + "with user.Login.length() <= 65535 after it registration.");
    }

    @Test
    void register_validUserMustNotThrowRuntimeException_Ok() {
        validUser.setLogin("validUserForExceptionTest");
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) must NOT throw RuntimeException.");
        }
    }

    @Test
    void register_validUserAddedToStorage_Ok() {
        validUser.setLogin(validUser.getLogin() + "0"); // for get unique login
        int expectedSize = Storage.people.size() + 1;
        registrationService.register(validUser);
        int actualSize = Storage.people.size();
        assertTrue(Storage.people.contains(validUser),
                "Storage.people must contains validUser after it registration.");
        assertEquals(expectedSize, actualSize,
                "Storage.people.size() must be incremented after registration validUser.");
    }

    @Test
    void register_invalidUserAddedToStorage_NotOk() {
        invalidUser.setAge(1);
        int expectedSize = Storage.people.size();
        try {
            registrationService.register(invalidUser);
        } catch (RuntimeException e) {
            String exceptionMessage = e.getMessage();
        } finally {
            int actualSize = Storage.people.size();
            assertFalse(Storage.people.contains(invalidUser),
                    "Storage.people must NOT contains invalidUser after try it registration.");
            assertEquals(expectedSize, actualSize,
                    "Storage.people.size() must be NOT changed "
                            + "after try registration invalidUser.");
        }
    }
}
