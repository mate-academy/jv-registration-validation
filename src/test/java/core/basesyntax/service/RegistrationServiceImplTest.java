package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationServiceImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void clear() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        User testUser = new User("giteras", "passpass", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User(null, "passpass", 44);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User("giteras", null, 58);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageUnderEighteen_notOk() {
        User testUser = new User("giteras", "passpass", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageUnderNull_notOk() {
        User testUser = new User("giteras", "passpass", -17);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageAboveEighteenIncluded_Ok() {
        User testUser = new User("giteras", "passpass", 18);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_loginUpToSix_notOk() {
        User testUser = new User("giter", "passpass", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginFromSix_Ok() {
        User testUser = new User("giterass", "passpasss", 65);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_passwordUpToSix_notOk() {
        User testUser = new User("giter", "passp", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordFromSix_Ok() {
        User testUser = new User("giterasss", "passpassss", 25);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_validUser_Ok() {
        User testUser = new User("giterr", "passpa", 19);
        assertEquals(registrationService.register(testUser),testUser);
    }

    @Test
    void register_existedUser_notOk() {
        User testUser = new User("giterr", "passpa", 19);
        registrationService.register(testUser); // реєструємо першого користувача
        User sameUser = new User("giterr", "anotherPass", 22);
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(sameUser));
        assertEquals("User is already exists", exception.getMessage());
    }
}
