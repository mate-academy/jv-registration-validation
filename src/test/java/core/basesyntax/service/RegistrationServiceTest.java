package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException; // Правильний імпорт винятку
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private StorageDaoImpl storage;

    @Before
    public void setUp() {
        storage = new StorageDaoImpl(); // Використовуємо справжню реалізацію
        registrationService = new RegistrationServiceImpl(storage);
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User("validLogin", "validPassword", 20);
        registrationService.register(validUser);
        assertEquals(validUser, storage.get(validUser.getLogin()));
    }

    @Test
    public void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User(null, "validPassword", 20);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login cannot be null", exception.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User("validLogin", null, 20);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User("short", "validPassword", 20);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User("validLogin", "short", 20);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    public void register_ageTooYoung_notOk() {
        User user = new User("validLogin", "validPassword", 17);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        User existingUser = new User("existingLogin", "validPassword", 20);
        storage.add(existingUser);

        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(existingUser));
        assertEquals("User with this login already exists", exception.getMessage());
    }

}
