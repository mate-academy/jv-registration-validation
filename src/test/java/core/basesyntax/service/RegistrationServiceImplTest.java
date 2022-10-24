package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        },"register(user) with user == null should throw RuntimeException.");
    }

    @Test
    void register_userAgeNull_NotOk() {
        invalidUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Age == null should throw RuntimeException.");
    }

    @Test
    void register_userInvalidAgeThrowExceptionAboutAge_Ok() {
        invalidUser.setAge(0);
        try {
            registrationService.register(invalidUser);
        } catch (RuntimeException e) {
            boolean exceptionMessageContainsAge = e.getMessage().toLowerCase().contains("age");
            assertTrue(exceptionMessageContainsAge,
                    "register(user) with invalid age should throw RuntimeException "
                            + "with message contains \"age\".");
            return;
        }
        fail("register(user) with invalid age should throw RuntimeException.");
    }

    @Test
    void register_userAge16_NotOk() {
        invalidUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Age == 16 should throw RuntimeException.");
    }

    @Test
    void register_userAgeNegative_NotOk() {
        invalidUser.setAge(-30);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with negative age should throw RuntimeException.");
    }

    @Test
    void register_userAge18_Ok() {
        validUser.setLogin("validUserForAge18Test");
        validUser.setAge(18);
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Age >= 18 should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_userAge25_Ok() {
        validUser.setLogin("validUserForAge25Test");
        validUser.setAge(25);
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Age >= 18 should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_userAgeIntegerMaxValue_Ok() {
        validUser.setLogin("validUserForAgeBigIntegerTest");
        validUser.setAge(Integer.MAX_VALUE);
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Age == Integer.MAX_VALUE "
                    + "should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_userPasswordNull_NotOk() {
        invalidUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password == null  should throw RuntimeException.");
    }

    @Test
    void register_userInvalidPasswordThrowExceptionAboutPassword_Ok() {
        invalidUser.setPassword("0");
        try {
            registrationService.register(invalidUser);
        } catch (RuntimeException e) {
            boolean exceptionMessageContainsPassword =
                    e.getMessage().toLowerCase().contains("password");
            assertTrue(exceptionMessageContainsPassword,
                    "register(user) with invalid password should throw RuntimeException "
                            + "with message contains \"password\".");
            return;
        }
        fail("register(user) with invalid password should throw RuntimeException.");
    }

    @Test
    void register_userPasswordEmpty_NotOk() {
        invalidUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Password should throw RuntimeException.");
    }

    @Test
    void register_userPasswordLength5_NotOk() {
        invalidUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Password.length() < 6  should throw RuntimeException.");
    }

    @Test
    void register_userPasswordLength6_Ok() {
        validUser.setLogin("validUserForPasswordLength6Test");
        validUser.setPassword("a2c4e6");
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Password.length() >= 6 "
                    + "should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_userPasswordVeryLong_Ok() {
        validUser.setLogin("validUserForPasswordVeryLongTest");
        String symbol = "a";
        String passwordVeryLong = symbol.repeat(1 << 16);
        validUser.setPassword(passwordVeryLong);
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Password.length() <= 65535 "
                    + "should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_userLoginNull_NotOk() {
        invalidUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with user.Login == null should throw RuntimeException.");
    }

    @Test
    void register_userLoginEmpty_NotOk() {
        invalidUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUser);
        },"register(user) with empty user.Login should throw RuntimeException.");
    }

    @Test
    void register_userExistingLoginThrowExceptionAboutLogin_Ok() {
        validUser.setLogin("validUserForLoginTest");
        registrationService.register(validUser);
        invalidUser.setLogin("validUserForLoginTest");
        try {
            registrationService.register(invalidUser);
        } catch (RuntimeException e) {
            boolean exceptionMessageContainsLogin = e.getMessage().toLowerCase().contains("login");
            assertTrue(exceptionMessageContainsLogin,
                    "register(user) with existing login should throw RuntimeException "
                            + "with message contains \"login\".");
            return;
        }
        fail("register(user) with existing login should throw RuntimeException.");
    }

    @Test
    void register_userLoginVeryLong_Ok() {
        String symbol = "a";
        String loginVeryLong = symbol.repeat(1 << 16);
        validUser.setLogin(loginVeryLong);
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) with user.Login.length() <= 65535 "
                    + "should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_validUserShouldNotThrowRuntimeException_Ok() {
        validUser.setLogin("validUserForExceptionTest");
        try {
            registrationService.register(validUser);
        } catch (RuntimeException e) {
            fail("register(validUser) should NOT throw RuntimeException.");
        }
    }

    @Test
    void register_validUserAddedToStorage_Ok() {
        validUser.setLogin(validUser.getLogin() + "0"); // for get unique login
        int expectedSize = Storage.people.size() + 1;
        User actualUser = registrationService.register(validUser);
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
                    "Storage.people must not contains invalidUser after try it registration.");
            assertEquals(expectedSize, actualSize,
                    "Storage.people.size() must be not changed "
                            + "after try registration invalidUser.");
        }
    }
}
