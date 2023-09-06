package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.customexception.CustomException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_UserIsValid_Ok() {
        user = new User("Arsenal", "Ar789al", 20);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserNotValid_notOk() {
        user = new User("daff", "gadaz", 11);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOk() {
        user = new User(null, "validity", 21);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user = new User("Arsenal", null, 21);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_ThrowsException() {
        user = new User("Arsenal", "Ar789al", 11);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void user_EmptyLogin_NotOk() {
        user = new User("", "Ar789al", 21);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void user_OverLoginLength_Ok() {
        user = new User("Arsenal2132", "Ar789al", 21);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_NotEnoughPasswordLength_NotOk() {
        user = new User("Arsenal", "null", 21);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void user_OverPasswordLength_Ok() {
        user = new User("Arsenal", "Ar789al789", 21);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void user_LessAge_NotOk() {
        user = new User("Arsenal", "Ar789al", 11);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void user_OverAge_Ok() {
        user = new User("Arsenal", "Ar789al", 25);
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }
}
