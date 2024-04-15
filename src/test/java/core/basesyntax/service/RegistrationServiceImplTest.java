package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLoginUser_NotOk() {
        User user = new User(null, "123456", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        User user = new User("GodLogin", null, 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAgeUser_NotOk() {
        User user = new User("GodLogin", "123456", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginUser_NotOk() {
        User user = new User("12345", "12345678", 30);
        User user2 = new User("1234", "12345678", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_shortPasswordUser_NotOk() {
        User user = new User("GodLogin", "12345", 30);
        User user2 = new User("GodLogin", "1234", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_inappropriateAgeUser_NotOk() {
        User user = new User("GodLogin", "123456", -1);
        User user2 = new User("GodLogin", "123456", 0);
        User user3 = new User("GodLogin", "123456", 5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        User user4 = new User("GodLogin", "123456", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        User user5 = new User("GodLogin", "123456", 101);
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
    }

    @Test
    void register_goodUserExample_Ok() {
        User user1 = new User("GodLogin1", "123456", 18);
        User user2 = new User("GodLogin2", "123456", 19);
        User user3 = new User("GodLogin3", "123456", 100);
        User actualUser1 = registrationService.register(user1);
        User actualUser2 = registrationService.register(user2);
        User actualUser3 = registrationService.register(user3);
        assertEquals(user1, actualUser1);
        assertEquals(user2, actualUser2);
        assertEquals(user3, actualUser3);
    }

    @Test
    void register_existingUser_NotOk() {
        User user1 = new User("GodLogin", "123456", 18);
        User user2 = new User("GodLogin", "7654321", 30);
        User actualUser1 = registrationService.register(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }
}
