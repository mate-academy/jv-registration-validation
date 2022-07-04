package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(12345L);
        user.setLogin("Sergey");
        user.setPassword("123456");
        user.setAge(20);
    }

    @Test
    void registerUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual, "The user added was not added, an error occurred");
    }

    @Test
    void registerTwoIdenticalUser_NotOk() {
        User firstUser = user;
        User secondUser = user;
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void registerUserNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerUserShortPassword_NotOk() {
        user.setPassword("1234");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserPasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeYoung_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
