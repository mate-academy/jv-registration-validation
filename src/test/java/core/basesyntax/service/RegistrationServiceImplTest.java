package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void duplicateLoginIs_notOk() {
        User user1 = new User(1234L, "login254", "1234567", 20);
        User user2 = new User(1234L, "login254", "1234567890", 20);
        registrationService.register(user1);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void userLoginIs_NotOk() {
        User actual = new User(1234L, "logi", "1234567", 20);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userPasswordIs_NotOk() {
        User actual = new User(1234L, "login14778", "123", 20);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userAgeIs_NotOk() {
        User actual = new User(1234L, "login14778", "1232556", 10);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userLoginIs_Ok() {
        User actual = new User(1234L, "login25423", "1234567", 20);
        assertEquals(registrationService.register(actual),actual);
    }

    @Test
    void userPasswordIs_Ok() {
        User actual = new User(1234L, "login12", "12345672312", 20);
        assertEquals(registrationService.register(actual),
                actual, "Error. Custom message assert true");
    }

    @Test
    void userAgeIs_Ok() {
        User actual = new User(1234L, "login1212", "123452312", 28);
        assertEquals(registrationService.register(actual),
                actual, "Error. Custom message assert true");
    }
}
