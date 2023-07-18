package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Abracadabra");
        user.setAge(20);
        user.setPassword("arbadacarbA");
    }

    @Test
    void valid_user_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_with_acceptable_length_login_Ok() {
        user.setLogin("Crocodile");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_with_acceptable_length_password_Ok() {
        user.setPassword("Banana");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_with_acceptable_age_Ok() {
        user.setAge(20);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_with_negative_age_NotOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_null_login_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_empty_login_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_short_login_NotOk() {
        user.setLogin("usd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_null_password_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_empty_password_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void user_with_short_password_NotOk() {
        user.setPassword("eur");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}