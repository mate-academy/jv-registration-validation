package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("testLogin");
        user.setPassword("testPassword");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ZeroCharactersLogin_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ThreeCharactersLogin_notOk() {
        user.setLogin("abc");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_FiveCharactersLogin_notOk() {
        user.setLogin("abcde");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_SixCharactersLogin_Ok() {
        user.setLogin("abcdef");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_EightCharactersLogin_Ok() {
        user.setLogin("abcdefgh");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_ZeroCharactersPassword_notOk() {
        user.setPassword("");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ThreeCharactersPassword_notOk() {
        user.setPassword("abc");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_FiveCharactersPassword_notOk() {
        user.setPassword("abcde");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_SixCharactersPassword_Ok() {
        user.setPassword("abcdef");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_EightCharactersPassword_Ok() {
        user.setPassword("abcdefgh");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_NegativeAge_notOk() {
        user.setAge(-1);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_under18YearsOldAge_notOk() {
        user.setAge(16);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_18YearsOldAge_Ok() {
        user.setAge(18);
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_over18YearsOldAge_Ok() {
        user.setAge(21);
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userAlreadyInStorage_notOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
