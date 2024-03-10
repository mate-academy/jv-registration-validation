package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user123";
    private static final String INVALID_LOGIN = null;
    private static final String VALID_PASSWORD = "password123";
    private static final String INVALID_PASSWORD = "nvdf";
    private static final int VALID_AGE = 20;
    private static final int INVALID_AGE = 17;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @Test
    public void registerValidUser_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    public void registerUserWithNullLoginThrowRegistrationException_notOk() {
        user.setLogin(INVALID_LOGIN); // Use the constant for null/empty login
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login can't be null", exception.getMessage()); // Check exception message
    }

    @Test
    public void registerUserWithNullPasswordThrowRegistrationException_notOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithInvalidAgeThrowRegistrationException_notOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithShortPasswordThrowRegistrationException_notOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerUserWithShortLoginThrowRegistrationException_notOk() {
        user.setLogin(INVALID_LOGIN + "f"); // Append a character to make it non-empty
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
