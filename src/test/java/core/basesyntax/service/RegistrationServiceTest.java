package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User validUser = new User("validLogin", "validPassword", 18);
        assertDoesNotThrow(() -> registrationService.register(validUser));
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void register_existingLogin_notOk() {
        User existingUser = new User("existingLogin", "password123", 22);
        Storage.people.add(existingUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("existingLogin", "newPassword", 22)));
    }

    @Test
    void register_loginTooShort_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("short", "password123", 22)));
    }

    @Test
    void register_loginMinLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("length6", "password123", 22)));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(null, "password123", 22)));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", null, 22)));
    }

    @Test
    void register_passwordTooShort_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "short", 22)));
    }

    @Test
    void register_passwordMinLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("validLogin", "length6", 22)));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "password123", null)));
    }

    @Test
    void register_ageUnder18_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "password123", 17)));
    }

    @Test
    void register_ageMin_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("validLogin", "password123", 18)));
    }

    @Test
    void register_ageOver18_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("validLogin", "password123", 19)));
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "password123", -5)));
    }

    @Test
    void register_userAddedToStorage_ok() {
        User user = new User("newUser", "newPassword", 25);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }
}
