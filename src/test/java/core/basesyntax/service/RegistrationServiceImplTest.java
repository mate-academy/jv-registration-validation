package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storage;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        storage.add(registrationService.register(new User("HeroWizard", "Qwerty123!", 26)));
        storage.add(registrationService.register(new User("mrDiller227", "LoveYourMum!337", 29)));
    }

    @Test
    void register_newUser_Ok() {
        User user = new User("Tapochek", "heavYMet@l88", 19);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_alreadyRegisteredUser_notOk() {
        User user = new User("HeroWizard", "Qwerty123!", 26);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "User already have an account.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User("Jambo21", "heavYMset@l88", 17);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "You age must be more than 18.";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("littlguy21", "buYM1acoffe!", null);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "User age can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("lokomoto", null, 28);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Password can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("Kikilast", "lox12", 21);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Password length should be more than 6";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_weakPassword_notOk() {
        User user = new User("IosifSid", "aezakmi27", 29);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Weak password!";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "okin@w@19", 22);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Login can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("kia", "Ki@s0rento", 25);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Login length should be more than 6";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_unsupportedLogin_notOk() {
        User user = new User("1STKL@ss", "32123sDs@#", 33);
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        String actual = exception.getMessage();
        String expected = "Your login contains unsupported characters.";
        assertTrue(actual.contains(expected));
    }
}
