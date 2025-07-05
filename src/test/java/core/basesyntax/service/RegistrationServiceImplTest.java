package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User validUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();

        validUser = new User();
        validUser.setLogin("validLogin");
        validUser.setPassword("validPass");
        validUser.setAge(25);
    }

    @Test
    void register_validUser_ok() {
        User registered = registrationService.register(validUser);
        assertNotNull(registered);
        assertEquals(validUser.getLogin(), registered.getLogin());
    }

    @Test
    void register_duplicateLogin_notOk() {
        registrationService.register(validUser);

        User duplicate = new User();
        duplicate.setLogin("validLogin");
        duplicate.setPassword("anotherPass");
        duplicate.setAge(30);

        assertThrows(RegistrationException.class, () ->
                registrationService.register(duplicate));
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("abc");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("123");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_underageUser_notOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }
}
