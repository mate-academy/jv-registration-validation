package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void userAllFieldsNulls_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, null, null));
        });
    }

    @Test
    void userLoginNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "123456789", 19));
        });
    }

    @Test
    void userPasswordNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", null, 19));
        });
    }

    @Test
    void userAgeNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", "123456789", null));
        });
    }

    @Test
    void userLoginInvalid_notOk() {
        User user = new User("v1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userPasswordInvalid_notOk() {
        User user = new User("vadim1337", "1234", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAgeInvalid_notOk() {
        User user = new User("vadim1337", "123456789", 16);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userExistsInStorage_notOk() {
        User user1 = new User("vadim1337", "123456789", 19);
        registrationService.register(user1);
        User user2 = new User("vadim1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void validUser_Ok() {
        User user = new User("vadim1337", "123456789", 19);
        User registered = registrationService.register(user);
        assertEquals(user, registered);
    }
}