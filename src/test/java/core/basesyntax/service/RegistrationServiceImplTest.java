package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);

        User actual = registrationService.register(user);
        assertNotNull(actual);
        assertEquals(user.getLogin(), actual.getLogin());
    }

    @Test
    public void register_nullUser_NotOk() {
        User user = null;

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_youngUser_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(10);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_userWithNullAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_NotOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_userWithNullLogin_NotOk() {
        User user = new User();
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_userWithNullPassword_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
