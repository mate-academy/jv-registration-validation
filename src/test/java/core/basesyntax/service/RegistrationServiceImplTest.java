package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login66";
    private static final String VALID_PASSWORD = "pass66";
    private static final Integer VALID_AGE = 25;

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);

        User result = registrationService.register(user);

        assertNotNull(result);
        assertNotNull(result.getId(), "id should be set after adding to storage");
        assertTrue(Storage.people.contains(result));
    }

    @Test
    void register_existingLogin_notOk() {
        User existing = new User();
        existing.setLogin("sameLogin");
        existing.setPassword("anyPass");
        existing.setAge(30);
        Storage.people.add(existing);

        User newUser = new User();
        newUser.setLogin("sameLogin");
        newUser.setPassword("newPass");
        newUser.setAge(22);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User can't be null", ex.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login can't be null", ex.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        String[] badLogins = {"", "abc", "abcde"};
        for (String bad : badLogins) {
            User user = new User();
            user.setLogin(bad);
            user.setPassword(VALID_PASSWORD);
            user.setAge(VALID_AGE);
            assertThrows(RegistrationException.class, () -> registrationService.register(user),
                    "Should throw for login length: " + bad.length());
        }
        User ok = new User();
        ok.setLogin("abcdef");
        ok.setPassword(VALID_PASSWORD);
        ok.setAge(VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(ok));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password can't be null", ex.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        String[] badPasswords = {"", "abc", "abcde"}; // 0,3,5
        for (String bad : badPasswords) {
            User user = new User();
            user.setLogin(VALID_LOGIN + bad); // ensure unique login
            user.setPassword(bad);
            user.setAge(VALID_AGE);
            assertThrows(RegistrationException.class, () -> registrationService.register(user),
                    "Should throw for password length: " + bad.length());
        }
        User ok = new User();
        ok.setLogin("loginOK");
        ok.setPassword("abcdef");
        ok.setAge(VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(ok));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Age can't be null", ex.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(-1);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertTrue(ex.getMessage().contains("Age can't be negative"));
    }

    @Test
    void register_under18_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(17);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertTrue(ex.getMessage().contains("Min allowed age"));
    }

    @Test
    void register_age18_ok() {
        User user = new User();
        user.setLogin("age18login");
        user.setPassword(VALID_PASSWORD);
        user.setAge(18);

        User result = registrationService.register(user);
        assertNotNull(result.getId());
        assertTrue(Storage.people.contains(result));
    }
}
