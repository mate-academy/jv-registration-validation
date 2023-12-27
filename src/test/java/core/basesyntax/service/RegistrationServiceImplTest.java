package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullFields_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, null, null));
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "123456789", 19));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", null, 19));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("vadim1337", "123456789", null));
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("v1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setLogin("v13");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setLogin("v1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("vadim1337", "1234", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setPassword("1qwee");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setPassword("vbn");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotOldEnough_notOk() {
        User user = new User("vadim1337", "123456789", 15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setAge(13);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setAge(-2);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userExistsInStorage_notOk() {
        User user1 = new User("vadim1337", "123456789", 19);
        registrationService.register(user1);
        User user2 = new User("vadim1337", "123456789", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_validUser_Ok() {
        User user1 = new User("vadim133798", "123456789", 19);
        User registered1 = registrationService.register(user1);
        assertEquals(user1, registered1);

        User user2 = new User("wetedfg2314", "ytrhfgvbfgh11", 33);
        User registered2 = registrationService.register(user2);
        assertEquals(user2, registered2);

        User user3 = new User("wetedfg", "ytrhfgvbfgh", 33);
        User registered3 = registrationService.register(user3);
        assertEquals(user3, registered3);
    }
}
