package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setPassword("123456");
        user.setLogin("CorrectName");
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(130);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if age more than 120 an exception should be thrown");
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if age less than 18 an exception should be thrown");
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if age null an exception should be thrown");
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if password null an exception should be thrown");
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_invalidLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if login null an exception should be thrown");
        user.setLogin("q");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        },"InvalidDataException,"
                + "if login length less than 2 an exception should be thrown");
    }

    @Test
    void register_validPassword_Ok() {
        user.setPassword("1234ab");
        user.setLogin("bob");
        Long actualId = user.getId();
        registrationService.register(user);
        assertNotEquals(actualId, user.getId());
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(18);
        Long actualId = user.getId();
        registrationService.register(user);
        assertNotEquals(actualId, user.getId());
    }

    @Test
    void register_tryAddAnotherUserWithSameLogin_NotOk() {
        user.setLogin("Bob");
        registrationService.register(user);
        User userSameLogin = new User();
        userSameLogin.setLogin("Bob");
        userSameLogin.setAge(19);
        userSameLogin.setPassword("123456");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userSameLogin);
        });
    }
}
