package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

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
    void register_zeroCharactersLogin_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_threeCharactersLogin_notOk() {
        user.setLogin("abc");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_fiveCharactersLogin_notOk() {
        user.setLogin("abcde");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLengthLogin_Ok() {
        user.setLogin("abcdef");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_eightCharactersLogin_Ok() {
        user.setLogin("abcdefgh");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_zeroCharactersPassword_notOk() {
        user.setPassword("");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_threeCharactersPassword_notOk() {
        user.setPassword("abc");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_fiveCharactersPassword_notOk() {
        user.setPassword("abcde");
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLengthPassword_Ok() {
        user.setPassword("abcdef");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_eightCharactersPassword_Ok() {
        user.setPassword("abcdefgh");
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minAge_Ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        Assertions.assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_overMinAge_Ok() {
        user.setAge(MIN_AGE + 1);
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
