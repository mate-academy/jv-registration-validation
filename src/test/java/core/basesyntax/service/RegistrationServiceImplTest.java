package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() throws UserRegistrationException {
        User user = new User("Bogdan", "krutoi228", 88);
        User registeredUser = registrationService.register(user);
        assertTrue(Storage.people.contains(registeredUser));
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExists_notOk() throws UserRegistrationException {
        User user = new User("oleksandr", "average", 87);
        registrationService.register(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthLessThanMin_notOk() {
        User user = new User("gachi", "orlcmsht", 26);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        User user = new User("children", "asdgf", 26);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "qwe123", 34);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("arthas", null, 65);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("loginNotNull", "passwordNotNull", null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageMoreThanMin_notOk() {
        User user = new User("shadow_fiend", "zxc123", 9);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

}
