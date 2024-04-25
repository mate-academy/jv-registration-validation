package core.basesyntax.service;

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

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
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
        User user = new User();
        user.setPassword("thisPasswordIsFit");
        user.setAge(21);
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for null login");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        User user = new User();
        user.setPassword("thisPasswordIsFit");
        user.setAge(21);
        user.setLogin("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for empty login");
    }

    @Test
    void register_loginIsLessThanMinLength_notOk() {
        User user = new User();
        user.setPassword("thisPasswordIsFit");
        user.setAge(21);
        user.setLogin("12345");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for login"
                        + user.getLogin());
    }

    @Test
    void register_loginMinLength_Ok() {
        User expected = new User();
        expected.setLogin("login6");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(21);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_loginIsFit_Ok() {
        User expected = new User();
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(21);
        expected.setLogin("login@1454sd");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword(null);
        user.setAge(21);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for null password");
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("");
        user.setAge(21);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for empty password");
    }

    @Test
    void register_passwordLessThanSix_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("123");
        user.setAge(21);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for password"
                        + user.getPassword());
    }

    @Test
    void register_passwordMinLength_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("123456");
        expected.setAge(21);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_passwordIsFit_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("12345678910");
        expected.setAge(21);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("thisPasswordIsFit");
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for null age");
    }

    @Test
    void register_userAgeIsLessThanZero_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("thisPasswordIsFit");
        user.setAge(-25);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for negative age");
    }

    @Test
    void register_userAgeLessThanMinAge_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("thisPasswordIsFit");
        user.setAge(17);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException for age"
                        + user.getAge());
    }

    @Test
    void register_minAge_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_maxValueAge_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(Integer.MAX_VALUE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_ageIsFit_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(50);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userLoginAlreadyExist_notOk() {
        User user = new User();
        user.setLogin("thisLoginIsFit");
        user.setPassword("thisPasswordIsFit");
        user.setAge(50);
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },
                "It must be thrown UserRegistrationException user that already exist");
    }

    @Test
    void register_returnsTheSameUser_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(50);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userActuallyWasAddedToStorage_ok() {
        User expected = new User();
        expected.setLogin("thisLoginIsFit");
        expected.setPassword("thisPasswordIsFit");
        expected.setAge(50);
        User actual = registrationService.register(expected);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_nonFitUserWasNotAddedToStorage_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("thisPasswordIsFit");
        user.setAge(50);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertFalse(Storage.people.contains(user));
    }
}
