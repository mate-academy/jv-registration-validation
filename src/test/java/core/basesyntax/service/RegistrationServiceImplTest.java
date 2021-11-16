package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login@gmail.com");
        user.setPassword("normalPassword");
        user.setAge(18);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registration.register(null)
        );
    }

    @Test
    void register_userIsAlreadyInStorage_NotOk() {
        user.setLogin("newUser@gmail.com");
        registration.register(user);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userAgeIsLessThanEighteen_NotOk() {
        user.setAge(-228);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_passwordLengthIsLessThanSix_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithEmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registration.register(user));
    }
}
