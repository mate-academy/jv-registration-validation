package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User userWithCorrectFields;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        userWithCorrectFields = new User();
        userWithCorrectFields.setLogin("thisLoginIsFit");
        userWithCorrectFields.setPassword("thisPasswordIsFit");
        userWithCorrectFields.setAge(21);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(null);
        },
                "It must be thrown UserRegistrationException for null user");
    }

    @Test
    void register_nullLogin_notOk() {
        userWithCorrectFields.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for null login");
    }

    @Test
    void register_loginLessThanSixCharacters_notOk() {
        userWithCorrectFields.setLogin("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for empty login");

        userWithCorrectFields.setLogin("123");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for login"
                        + userWithCorrectFields.getLogin());

        userWithCorrectFields.setLogin("12345");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for login"
                        + userWithCorrectFields.getLogin());
    }

    @Test
    void register_loginMinLength_Ok() {
        userWithCorrectFields.setLogin("login6");
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_loginIsFit_Ok() {
        userWithCorrectFields.setLogin("login@1454sd");
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_nullPassword_notOk() {
        userWithCorrectFields.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for null password");
    }

    @Test
    void register_passwordLessThanSix_notOk() {
        userWithCorrectFields.setPassword("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for empty password");

        userWithCorrectFields.setPassword("123");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for password"
                        + userWithCorrectFields.getPassword());

        userWithCorrectFields.setPassword("13245");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for password"
                        + userWithCorrectFields.getPassword());
    }

    @Test
    void register_passwordMinLength_ok() {
        userWithCorrectFields.setPassword("123456");
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_passwordIsFit_ok() {
        userWithCorrectFields.setPassword("12345678910");
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_nullAge_notOk() {
        userWithCorrectFields.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for null age");
    }

    @Test
    void register_userAgeLessThanMinAge_notOk() {
        userWithCorrectFields.setAge(-25);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for negative age");

        userWithCorrectFields.setAge(0);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for age"
                        + userWithCorrectFields.getAge());

        userWithCorrectFields.setAge(17);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException for age"
                        + userWithCorrectFields.getAge());
    }

    @Test
    void register_minAge_ok() {
        userWithCorrectFields.setAge(18);
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_maxValueAge_ok() {
        userWithCorrectFields.setAge(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_ageIsFit_ok() {
        userWithCorrectFields.setAge(50);
        assertDoesNotThrow(() -> {
            registrationService.register(userWithCorrectFields);
        },
                "User " + userWithCorrectFields
                        + " have to register success, but was thrown exception");
    }

    @Test
    void register_userLoginAlreadyExist_notOk() {
        Storage.people.add(userWithCorrectFields);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        },
                "It must be thrown UserRegistrationException user that already exist");
    }

    @Test
    void register_returnsTheSameUser_ok() {
        User expected = userWithCorrectFields;
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userActuallyWasAddedToStorage_ok() {
        User user = registrationService.register(userWithCorrectFields);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nonFitUserWasNotAddedToStorage_notOk() {
        userWithCorrectFields.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(userWithCorrectFields);
        });
        assertFalse(Storage.people.contains(userWithCorrectFields));
    }
}
