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
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () ->
                registration.register(null)
        );
    }

    @Test
    void register_userIsAlreadyInStorage_notOk() {
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userAgeIsLessThanEighteen_notOk() {
        user.setAge(-228);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_passwordLengthIsLessThanSix_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_userWithEmptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void register_validUser_ok() {
        assertEquals(user, registration.register(user));
    }
}
