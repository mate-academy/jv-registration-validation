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
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("validUser");
        user.setAge(18);
        user.setPassword("password");
        Storage.people.clear();
    }

    @Test
    void registerNullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        }, "When User is null, the method should throw RuntimeException");
    }

    @Test
    void registerUserWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When login is null, the method should throw RuntimeException");
    }

    @Test
    void registerUserWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is null, the method should throw RuntimeException");
    }

    @Test
    void registerUserWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is null, the method should throw RuntimeException");
    }

    @Test
    void registerValidUser_Ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual, "Test failed! Method should return registered user");
        int expectedSize = 1;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize, "Size of storage should be 1");
    }

    @Test
    void registerTheSameUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When user already registered, the method should throw RuntimeException");
    }

    @Test
    void registerUserWithNotValidPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is less then 6 characters, the method should throw RuntimeException");
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When password is empty, the method should throw RuntimeException");
    }

    @Test
    void registerUserWithNotValidAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is less than 18, the method should throw RuntimeException");
        user.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "When age is negative, the method should throw RuntimeException");
    }
}
