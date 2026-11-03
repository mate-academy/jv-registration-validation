package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
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

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = createUser("user123", "pass123", 20);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertNotNull(actual.getId());
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "pass123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser("abc", "pass123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginWithFiveCharacters_notOk() {
        User user = createUser("abcde", "pass123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginWithSixCharacters_ok() {
        User user = createUser("abcdef", "pass123", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser("user123", null, 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createUser("user123", "123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordWithFiveCharacters_notOk() {
        User user = createUser("user123", "12345", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordWithSixCharacters_ok() {
        User user = createUser("user123", "123456", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser("user123", "pass123", null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageBelowEighteen_notOk() {
        User user = createUser("user123", "pass123", 17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageSeventeen_notOk() {
        User user = createUser("user123", "pass123", 17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageEighteen_ok() {
        User user = createUser("user123", "pass123", 18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User firstUser = createUser("user123", "pass123", 20);
        User secondUser = createUser("user123", "other123", 25);

        registrationService.register(firstUser);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser));

        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_differentLogin_ok() {
        User firstUser = createUser("user123", "pass123", 20);
        User secondUser = createUser("user456", "pass456", 25);

        registrationService.register(firstUser);
        registrationService.register(secondUser);

        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_invalidUser_storageRemainsEmpty() {
        User user = createUser("abc", "pass123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createUser("user123", "pass123", -1);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = createUser("", "pass123", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createUser("user123", "", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginWithEightCharacters_ok() {
        User user = createUser("user1234", "pass123", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordWithEightCharacters_ok() {
        User user = createUser("user123", "pass1234", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
