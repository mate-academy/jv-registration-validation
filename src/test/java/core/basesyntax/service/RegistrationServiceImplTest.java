package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
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
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLoginUser_notOk() {
        User user = new User(null, "123456", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPasswordUser_notOk() {
        User user = new User("GoodLogin", null, 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAgeUser_notOk() {
        User user = new User("GoodLogin", "123456", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginUser_notOk() {
        User user = new User("12345", "12345678", 30);
        User user2 = new User("1234", "12345678", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_shortPasswordUser_notOk() {
        User user = new User("GoodLogin", "12345", 30);
        User user2 = new User("GoodLogin", "1234", 30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_inappropriateAgeUser_notOk() {
        User user = new User("GoodLogin", "123456", -1);
        User user2 = new User("GoodLogin", "123456", 0);
        User user3 = new User("GoodLogin", "123456", 5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        User user4 = new User("GoodLogin", "123456", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user4));
        User user5 = new User("GoodLogin", "123456", 101);
        assertThrows(RegistrationException.class, () -> registrationService.register(user5));
    }

    @Test
    void register_goodUserExample_Ok() {
        User user1 = new User("Good12", "123456", 18);
        User user2 = new User("123456", "123456", 19);
        User user3 = new User("GoodLogin", "123456", 100);
        User actualUser1 = registrationService.register(user1);
        User actualUser2 = registrationService.register(user2);
        User actualUser3 = registrationService.register(user3);
        assertEquals(user1, actualUser1);
        assertEquals(user2, actualUser2);
        assertEquals(user3, actualUser3);
    }

    @Test
    void register_existingUser_notOk() {
        User user1 = new User("GoodLogin", "123456", 18);
        User user2 = new User("GoodLogin", "7654321", 30);
        Storage.people.add(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }
}
