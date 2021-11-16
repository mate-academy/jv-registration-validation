package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(19);
        user.setLogin("evreii");
        user.setPassword("helloMates");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validAge_ok() {
        user.setAge(20);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_validPassword_ok() {
        user.setPassword("helloMates");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_validUser_ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }
}
