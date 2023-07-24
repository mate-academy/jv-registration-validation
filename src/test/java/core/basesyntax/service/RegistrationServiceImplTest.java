package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String LOGIN = "Oleksandr";
    private static final String PASSWORD = "rytop12Qt";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setAge(MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minLogin_notOk() {
        user.setLogin("Osa");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minPassword_notOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minAge_notOk() {
        user.setAge(16);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctLogin_ok() {
        user.setLogin("CorrectLogin");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctPassword_ok() {
        user.setPassword("qwerty987");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_correctAge_ok() {
        user.setAge(20);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
