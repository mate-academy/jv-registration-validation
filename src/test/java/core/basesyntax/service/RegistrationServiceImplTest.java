package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void registerValidUse_Ok() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("password123");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    public void registerUserWithNullLoginThrowRegistrationException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithNullPasswordThrowRegistrationException() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithInvalidAgeThrowRegistrationException() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("password123");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithShortPasswordThrowRegistrationException() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("nvdf");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithShortLoginThrowRegistrationException() {
        User user = new User();
        user.setLogin("wsf");
        user.setPassword("gsvcasdcsd");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
