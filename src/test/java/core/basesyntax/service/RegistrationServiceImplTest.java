package core.basesyntax.service;

import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User("user", "password123", 25);
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class, () -> registrationService.register(user));
        assertEquals(
                "Login must be at least 6 characters long.",
                exception.getMessage());
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User("user1236666", "pass", 25);
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class, () -> registrationService.register(user));
        assertEquals(
                "Password must be at least 6 characters long.",
                exception.getMessage());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User("user123", "password123", 0);
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class, () -> registrationService.register(user));
        assertEquals(
                "User must be at least 18 years old.",
                exception.getMessage());
    }

    @Test
    public void register_underage_notOk() {
        User user = new User("user123", "password123", 17);
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class, () -> registrationService.register(user));
        assertEquals(
                "User must be at least 18 years old.",
                exception.getMessage());
    }

    @Test
    public void register_userAlreadyExists_notOk() {
        User user1 = new User("user123", "password123", 25);
        User user2 = new User("user123", "password1234", 26);
        registrationService.register(user1);
        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class, () -> registrationService.register(user2));
        assertEquals(
                "User with this login already exists.",
                exception.getMessage());
    }
}
