package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
        registrationService.register(new User("boblogin", "bobpassword", 28));
        registrationService.register(new User("alicelogin", "alicepassword", 19));
        registrationService.register(new User("johnlogin", "johnpassword", 23));
    }

    @Test
    void register_NullLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "johnpassword", 23));
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", null, 19));
        });
    }

    @Test
    void register_NullAge_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "alicepassword", null));
        });
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "alicepassword", 17));
        });
    }

    @Test
    void register_PasswordLengthlessThan6_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "login", 19));
        });
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("boblogin", "newpassword", 23));
        });
    }

    @Test
    void register_AgeMoreThanMax_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "newpassword", 158));
        });
    }

    @Test
    void registrationCompleteForThreeUsers_Ok() {
        List<User> actual = Storage.people;
        assertEquals(3, actual.size());
        assertTrue(actual.contains(new User("boblogin", "bobpassword", 28)));
        assertTrue(actual.contains(new User("alicelogin", "alicepassword", 19)));
        assertTrue(actual.contains(new User("johnlogin", "johnpassword", 23)));
    }
}
