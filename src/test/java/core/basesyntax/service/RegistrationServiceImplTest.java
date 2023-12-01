package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Bobusun", "bob123", 18);
    }

    @Test
    void register_ok() {
        Storage.people.add(user);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullValue_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_notOk() {
        User user = new User(null, "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_notOk() {
        User user = new User("Bobusun", null, 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_notOk() {
        User user = new User("Bobusun", "bob123", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyLogin_notOk() {
        User user = new User("", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void emptyPassword_notOk() {
        User user = new User("Bobusun", "", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void negativeAge_notOk() {
        User user = new User("Bobusun", "bob123", -1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginTooShort_notOk() {
        User user = new User("Bobys", "bob123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordTooShort_notOk() {
        User user = new User("Bobusun", "bob12", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void validAge_notOk() {
        User user = new User("Bobusun", "bob123", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
