package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static Storage storage;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
        user = new User(1234L, "login254", "1234567", 18);
    }

    @Test
    void duplicateLoginIs_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userLoginIs_NotOk() {
        User actual = new User(1234L, "login", "1234567", 18);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userPasswordIs_NotOk() {
        User actual = new User(1234L, "login14778", "12345", 18);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userAgeIs_NotOk() {
        User actual = new User(1234L, "login14778", "1232556", 17);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(actual);
        });
    }

    @Test
    void userLoginIs_Ok() {
        User actual = new User(1234L, "login2", "1234567", 18);
        assertEquals(registrationService.register(actual), actual);
    }

    @Test
    void userPasswordIs_Ok() {
        User actual = new User(1234L, "login12", "123456", 18);
        assertEquals(registrationService.register(actual),
                actual);
    }

    @Test
    void userAgeIs_Ok() {
        User actual = new User(1234L, "login1", "123456", 18);
        assertEquals(registrationService.register(actual),
                actual);
    }
}
