package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearDataBase() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User newUser = new User("Forzen123212", "forzen123212", 20);
        User registratedUser = registrationService.register(newUser);
        assertEquals(1, Storage.people.size());
        assertEquals(newUser, registratedUser);
    }

    @Test
    void register_nullUser_notOk() {
        User newUser = null;
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_existingUser_notOk() {
        User newUser = new User("Forzen123212", "forzen123212", 20);
        Storage.people.add(newUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User newUser = new User(null, "forzen123212", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User newUser = new User("forz", "forzen123212", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User newUser = new User("Forzen123212", null, 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User newUser = new User("forzen123212", "123", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_nullAge_notOk() {
        User newUser = new User("Forzen123212", "forzen123212", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User newUser = new User("forzen123212", "forzen123212", -20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_smallAge_notOk() {
        User newUser = new User("Nagibator2012", "nagibator2012", 11);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }
}
