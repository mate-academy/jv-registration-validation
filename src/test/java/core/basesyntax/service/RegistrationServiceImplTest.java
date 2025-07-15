package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(20);

        User registerUser = registrationService.register(user);

        assertNotNull(registerUser);
        assertNotNull(registerUser.getId());
        assertEquals("validUser", registerUser.getLogin());
        assertEquals("securePass", registerUser.getPassword());
        assertEquals(20, registerUser.getAge());
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_NotOk() {
        User user = new User();
        user.setLogin("user"); // < 6
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("123"); // < 6
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_NotOk() {
        User user = new User();
        user.setLogin("youngUser");
        user.setPassword("youngPassword");
        user.setAge(17); // < 18

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("duplicate");
        user1.setPassword("password1");
        user1.setAge(25);
        registrationService.register(user1); // first registration - ok

        User user2 = new User();
        user2.setLogin("duplicate"); // same login
        user2.setPassword("password2");
        user2.setAge(30);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setLogin(null); // null login
        user.setPassword("password");
        user.setAge(22);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null); // null password
        user.setAge(22);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nulAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password");
        user.setAge(null); // null age

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthExactly6_Ok() {
        User user = new User();
        user.setLogin("abc123"); // == 6
        user.setPassword("strongPassword");
        user.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthExactly6_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456"); // == 6
        user.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageExactly18_Ok() {
        User user = new User();
        user.setLogin("adultUser");
        user.setPassword("adultPassword");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
