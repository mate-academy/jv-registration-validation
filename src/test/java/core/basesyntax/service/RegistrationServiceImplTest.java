package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
    }

    @Test
    void registerTwoIdenticalLogins_NotOk() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setAge(20);
        User user2 = new User();
        user2.setLogin("login");
        user2.setPassword("password");
        user2.setAge(20);
        try {
            registrationService.register(user1);
            registrationService.register(user2);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown");
    }

    @Test
    void registerUserAgeSevenTeen_NotOk() {
        user.setAge(17);
        user.setPassword("password");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeEightTeen_Ok() {
        user.setAge(18);
        user.setLogin("Login123");
        user.setPassword("password");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeNineTeen_Ok() {
        user.setAge(19);
        user.setLogin("Login123");
        user.setPassword("password");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerPasswordContainsFiveSymbols_NotOk() {
        user.setPassword("passw");
        user.setAge(18);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown");
    }

    @Test
    void registerNotNullParameter_Ok() {
        user.setAge(34);
        user.setLogin("log123");
        user.setPassword("pass123");
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            fail();
        }

    }

    @Test
    void registerNullParameter_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerNullPassword_NotOk() {
        user.setAge(34);
        user.setLogin("log123");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerTwoDifferentUsers_NotOk() {
        User user1 = new User();
        user1.setAge(34);
        user1.setLogin("log321");
        user1.setPassword("password");
        User user2 = new User();
        user2.setAge(30);
        user2.setLogin("log1234");
        user2.setPassword("paSSword");
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void registerNullAge_NotOk() {
        user.setAge(null);
        user.setLogin("log123");
        user.setPassword("password");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNotNullValue_Ok() {
        user.setAge(25);
        user.setLogin("log12345");
        user.setPassword("password");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNullLogin_NotOk() {
        user.setAge(34);
        user.setLogin(null);
        user.setPassword("login365");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNegativeAgeValue_NotOk() {
        user.setLogin("LOgin345");
        user.setPassword("Password123");
        user.setAge(-25);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWhiteSpaceInLogin_NotOk() {
        user.setLogin("l gin567");
        user.setPassword("Password123");
        user.setAge(55);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerWhiteSpaceInPassword_NotOk() {
        user.setLogin("lOgin567");
        user.setPassword("Password1 3");
        user.setAge(55);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}














