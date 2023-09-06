package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final String defaultPassword = "228322";
    private final String defaultLogin = "228322";
    private final Integer defaultAge = 54;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_validUser_Ok() {
        User user = new User("validUser", defaultPassword, defaultAge);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_twoValidUser_Ok() {
        User user1 = new User("user1login", defaultPassword, defaultAge);
        assertEquals(user1, registrationService.register(user1));
        User user2 = new User("user2login", defaultPassword, defaultAge);
        assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsNull_NotOk() {
        User user = new User(null, defaultPassword, defaultAge);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_shortLogin_NotOk() {
        User user = new User("short", defaultLogin, defaultAge);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_existingLogin_NotOk() {
        User user = new User("existingLogin", defaultPassword, defaultAge);
        assertEquals(user, registrationService.register(user));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User user = new User(defaultLogin, null, defaultAge);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User(defaultLogin, "short", defaultAge);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        User user = new User(defaultLogin, defaultPassword, null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_notAdultAge_NotOk() {
        User user = new User(defaultLogin, defaultPassword, 4);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User(defaultLogin, defaultPassword, -4);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        assertFalse(Storage.PEOPLE.contains(user));
    }
}
