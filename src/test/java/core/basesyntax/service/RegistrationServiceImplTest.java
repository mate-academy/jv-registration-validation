package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NotValidationUser;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_NullUser_NotOk() {
        try {
            assertNull(registrationService.register(null));
        } catch (NotValidationUser e) {
            return;
        }
        fail("User can not be null");
    }

    @Test
    void register_NullLoginUser_NotOk() {
        User nullLoginUser = new User(null, "password", 22);
        assertThrows(NotValidationUser.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_NullPasswordUser_NotOk() {
        User nullPasswordUser = new User("normalLogin", null, 22);
        assertThrows(NotValidationUser.class, () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_ShortLoginUser_NotOk() {
        User shortLoginUser = new User("login", "password", 22);
        assertThrows(NotValidationUser.class, () -> registrationService.register(shortLoginUser));
    }

    @Test
    void register_ShortPasswordUser_NotOk() {
        User shortPasswordUser = new User("normalLogin", "pass", 22);
        assertThrows(NotValidationUser.class, () ->
                registrationService.register(shortPasswordUser));
    }

    @Test
    void register_LessAgeUser_NotOk() {
        User youngAgeUser = new User("normalLogin", "password", 12);
        assertThrows(NotValidationUser.class, () -> registrationService.register(youngAgeUser));
    }

    @Test
    void register_ValidCase_Ok() {
        User validUser = new User("validLogin", "validPassword", 42);
        User ordinaryUser = new User("Michael", "Fratello", 2013);
        assertEquals(validUser, registrationService.register(validUser));
        assertEquals(ordinaryUser, registrationService.register(ordinaryUser));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void registered_UsedLogin_NotOk() {
        User ordinaryUser = new User("Michael", "Fratello", 2013);
        registrationService.register(ordinaryUser);
        assertThrows(NotValidationUser.class, () -> registrationService.register(ordinaryUser));
    }
}
