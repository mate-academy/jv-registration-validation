package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearDataBase() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_validUser_ok() {
        User newUser = new User("ElonMusk99", "eLONmUSK((", 18);
        User registratedUser = registrationService.register(newUser);
        assertEquals(1, Storage.PEOPLE.size());
        assertEquals(newUser, registratedUser);
    }

    @Test
    void register_nullUser_notOk() {
        User newUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_existingUser_notOk() {
        User newUser = new User("ElonMusk99", "eLONmUSK((", 24);
        Storage.PEOPLE.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User newUser = new User(null, "eLONmUSK((", 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User newUser = new User("Musk2", "eLONmUSK((", 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User newUser = new User("ElonMusk99", null, 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User newUser = new User("ElonMusk99", "qw12", 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullAge_notOk() {
        User newUser = new User("ElonMusk99", "eLONmUSK((", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User newUser = new User("ElonMusk99", "eLONmUSK((", -23);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_smallAge_notOk() {
        User newUser = new User("ElonMusk99", "eLONmUSK((", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }
}
