package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void registerCorrectData_Ok() {
        User user = new User("adminos", "Qwerty", 20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registerIncorrectAgeUser_NotOk() {
        User user = new User("adminos", "Qwerty1", 15);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNotExistingAgeUser_NotOk() {
        User user = new User("adminos", "Qwerty1", 155);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerIncorrectLoginUser_NotOk() {
        User user = new User("admin", "Qwerty1", 20);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerIncorrectPasswordUser_NotOk() {
        User user = new User("adminos", "123", 20);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerAllIncorrectUserData_NotOk() {
        User user = new User("admin", "123", 15);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerTwoSameLoginsUsers_NotOk() {
        User user = new User("adminos", "Qwerty1", 20);
        User user2 = new User("adminos", "SorryFine", 34);
        assertThrows(ExistingLoginException.class, () -> {
            registrationService.register(user);
            registrationService.register(user2);
        });
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerNullLoginUser_NotOk() {
        User user = new User(null, "123", 15);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNullPasswordUser_NotOk() {
        User user = new User("admino", null, 15);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNullAgeUser_NotOk() {
        User user = new User("admino", "123", null);
        assertThrows(InvalidUsersDataException.class, () -> {
            registrationService.register(user);
        });
    }

}
