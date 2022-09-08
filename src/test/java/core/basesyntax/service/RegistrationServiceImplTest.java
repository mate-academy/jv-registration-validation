package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "RuntimeException should be thrown when User is null");
    }

    @Test
    void register_ageOver18User_Ok() {
        user = new User();
        user.setAge(25);
        user.setLogin("Bob");
        user.setPassword("rocket");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual, "User with age over 18 should be registered");
    }

    @Test
    void register_age18User_Ok() {
        user = new User();
        user.setAge(18);
        user.setLogin("Elise");
        user.setPassword("rocket1981");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual, "User with age 18 should be registered");
    }

    @Test
    void register_ageLower18User_notOk() {
        user = new User();
        user.setAge(17);
        user.setLogin("Melody");
        user.setPassword("rocket");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when age less then 18");
    }

    @Test
    void register_negativeAgeUser_notOk() {
        user = new User();
        user.setAge(-1);
        user.setLogin("Jane");
        user.setPassword("rocket");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when age is negative");
    }

    @Test
    void register_nullAgeUser_notOk() {
        user = new User();
        user.setAge(null);
        user.setLogin("Patrick");
        user.setPassword("rocket");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when Age is null");
    }

    @Test
    void register_existingUser_notOk() {
        user = new User();
        user.setAge(25);
        user.setLogin("John");
        user.setPassword("rocket");
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when User exists");
    }

    @Test
    void register_shortPasswordUser_notOk() {
        user = new User();
        user.setAge(35);
        user.setLogin("Sindy");
        user.setPassword("apple");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when password is shorter then 6 symbols");
    }

    @Test
    void register_nullPasswordUser_notOk() {
        user = new User();
        user.setAge(35);
        user.setLogin("Kate");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when password is null");
    }

    @Test
    void register_nullLoginUser_notOk() {
        user = new User();
        user.setAge(35);
        user.setLogin(null);
        user.setPassword("rocket");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown when login is null");
    }
}
