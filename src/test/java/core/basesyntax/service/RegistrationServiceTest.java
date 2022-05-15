package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userAgeLess18YearsOld_notOk() {
        User user = new User("user", "123456", 17);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeNegative_notOk() {
        User user = new User("user", "123456", -1);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessThanSixSymbol_notOk() {
        User user = new User("user", "12345", 18);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_suchLoginAlreadyExistedInTheStorage_notOk() {
        User loginAlreadyExist = new User("LoginAlreadyExist", "123456", 19);
        registrationService.register(loginAlreadyExist);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(loginAlreadyExist);
        });
    }

    @Test
    void register_userNull_notOk() {
        User actual = registrationService.register(null);

        assertNull(actual);
    }

    @Test
    void register_userLoginNull_notOk() {
        User user = new User(null, "123456", 18);
        User actual = registrationService.register(user);

        assertNull(actual);
    }

    @Test
    void register_userPasswordNull_notOk() {
        User user = new User("user", null, 18);
        User actual = registrationService.register(user);

        assertNull(actual);
    }

    @Test
    void register_userAgeNull_notOk() {
        User user = new User("user", "123456", 0);
        User actual = registrationService.register(user);

        assertNull(actual);
    }

    @Test
    void register_userWithCorrectFields_Ok() {
        User correctFields = new User("CorrectFields", "123456", 18);

        User actual = registrationService.register(correctFields);

        assertEquals(correctFields, actual);
    }

    @Test
    void register_userAge18YearsOld_Ok() {
        User age18 = new User("age18", "123456", 18);

        User actual = registrationService.register(age18);

        assertEquals(age18, actual);
    }

    @Test
    void register_userAgeOlderThan18YearsOld_Ok() {
        User olderThan18 = new User("OlderThan18", "123456", 22);

        User actual = registrationService.register(olderThan18);

        assertEquals(olderThan18, actual);
    }

    @Test
    void register_userPasswordContainsSixSymbols_Ok() {
        User passwordContainsSixSymbols = new User("PasswordContainsSixSymbols", "123456", 22);

        User actual = registrationService.register(passwordContainsSixSymbols);

        assertEquals(passwordContainsSixSymbols, actual);
    }

    @Test
    void register_userPasswordContainsMoreThanSixSymbols_Ok() {
        User passwordContainsMoreThanSixSymbols
                = new User("PasswordContainsMoreThanSixSymbols", "123456789", 22);

        User actual = registrationService.register(passwordContainsMoreThanSixSymbols);

        assertEquals(passwordContainsMoreThanSixSymbols, actual);
    }
}
