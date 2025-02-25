package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_nullAge_notOk() {
        User testUser = new User(323889801L, "giteras", "passpass", null);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullID_notOk() {
        User testUser = new User(null, "giteras", "passpass", 18);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User(323889801L, null, "passpass", 44);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User(323889801L, "giteras", null, 58);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageUnderEighteen_notOk() {
        User testUser = new User(323889801L, "giteras", "passpass", 17);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageAboveEighteenIncluded_Ok() {
        User testUser = new User(323889801L, "giteras", "passpass", 18);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_loginUpToSix_notOk() {
        User testUser = new User(323889801L, "giter", "passpass", 18);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginFromSix_Ok() {
        User testUser = new User(323888801L, "giterass", "passpasss", 65);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_passwordUpToSix_notOk() {
        User testUser = new User(323889801L, "giter", "passp", 18);
        assertThrows(UserNotProperException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordFromSix_Ok() {
        User testUser = new User(3238877801L, "giterasss", "passpassss", 25);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void notConteinsUser_Ok() {
        User testUser = new User(3238887801L, "giterr", "passpa", 19);
        assertEquals(registrationService.register(testUser),testUser);
    }
}
