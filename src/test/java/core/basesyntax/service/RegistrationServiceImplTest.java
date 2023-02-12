package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.customexception.ValidationExceptionIncorrectValue;
import core.basesyntax.customexception.ValidationExceptionNullValue;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
        Storage.people.clear();
    }

    @Test
    void containsUserCorrectData_OK() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("CorrectPassword");
        User expected = testUser;
        User actual = registrationService.register(testUser);
        assertEquals(expected, actual);
    }

    @Test
    void containsUserLoginInStorage_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("CorrectPassword");
        registrationService.register(testUser);
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isEmptyUserLogin_NotOk() {
        testUser.setLogin("");
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsWhitespaceUserLogin_NotOk() {
        testUser.setLogin("T e s t U s e r");
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsAgeUnderMinValue_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(-25);
        testUser.setPassword("CorrectPassword");
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsAgeMinValue_OK() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("CorrectPassword");
        User expected = testUser;
        User actual = registrationService.register(testUser);
        assertEquals(expected, actual);
    }

    @Test
    void containsPasswordUnderMinLength_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("abc");
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void containsWhiteSpaceInPassword_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword("a b c");
        assertThrows(ValidationExceptionIncorrectValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUser_NotOk() {
        testUser = null;
        assertThrows(ValidationExceptionNullValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUserLogin_NotOk() {
        testUser.setLogin(null);
        testUser.setAge(18);
        testUser.setPassword("CorrectPassword");
        assertThrows(ValidationExceptionNullValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullUserAge_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(null);
        testUser.setPassword("CorrectPassword");
        assertThrows(ValidationExceptionNullValue.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void isNullPassword_NotOk() {
        testUser.setLogin("CorrectLogin");
        testUser.setAge(18);
        testUser.setPassword(null);
        assertThrows(ValidationExceptionNullValue.class, () -> {
            registrationService.register(testUser);
        });
    }
}
