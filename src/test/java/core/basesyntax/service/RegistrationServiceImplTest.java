package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.custom.exception.UserValidationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User testUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setDefaultData() {
        testUser = new User();
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("CorrectPassword");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void containsUserCorrectData_OK() {
        User expected = testUser;
        User actual = registrationService.register(testUser);
        assertEquals(expected, actual);
    }

    @Test
    void containsUserLoginInStorage_NotOk() {
        registrationService.register(testUser);
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isEmptyUserLogin_NotOk() {
        testUser.setLogin("");
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsWhitespaceUserLogin_NotOk() {
        testUser.setLogin("T e s t U s e r");
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsAgeUnderMinValue_NotOk() {
        testUser.setAge(-25);
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsAgeMinValue_OK() {
        testUser.setAge(18);
        User expected = testUser;
        User actual = registrationService.register(testUser);
        assertEquals(expected, actual);
    }

    @Test
    void containsPasswordUnderMinLength_NotOk() {
        testUser.setPassword("abc");
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsWhiteSpaceInPassword_NotOk() {
        testUser.setPassword("a b c");
        assertThrows(core.basesyntax.custom.exception.UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUser_NotOk() {
        testUser = null;
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUserLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUserAge_NotOk() {
        testUser.setAge(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }
}
