package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.registrationexception.RegistrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_UserIsValid_Ok() {
        User user = new User("Arsenal", "Ar789al", 20);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_ExistingUser_NotOk() {
        User user = new User("Arsenal", "Ar789al", 20);
        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOk() {
        User user = new User(null, "validity", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User("Arsenal", null, 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_ThrowsException() {
        User user = new User("Arsenal", "Ar789al", 11);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_NotEnoughLogin_NotOk() {
        User user = new User("arsen", "Ar789al", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_OverLoginLength_Ok() {
        User user = new User("Arsenal2132", "Ar789al", 21);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_NotEnoughPasswordLength_NotOk() {
        User user = new User("Arsenal", "null", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_NegativeAge_NotOk() {
        User user = new User("Arsenal", "Ar789al789", -21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_LessAge_NotOk() {
        User user = new User("Arsenal", "Ar789al", 11);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_OverAge_Ok() {
        User user = new User("Arsenal", "Ar789al", 25);
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }
}
