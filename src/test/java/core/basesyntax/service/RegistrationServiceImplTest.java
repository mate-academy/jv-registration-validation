package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storage;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storage.add(new User("HeroWizard", "Qwerty123!", 26));
        storage.add(new User("mrDiller227", "LoveYourMum!337", 29));
    }

    @Test
    void register_validUser_Ok() {
        User user = new User("Tapochek", "heavYMet@l88", 19);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_alreadyRegisteredUser_notOk() {
        User user = new User("HeroWizard", "Qwerty123!", 26);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "User already have an account.";
        assertEquals(expected, actual);
    }

    @Test
    void register_youngAgeUser_notOk() {
        User youngUser = new User("Jambo21", "heavYMset@l88", 17);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(youngUser);
        }).getMessage();
        String expected = "You age must be more than 18.";
        assertTrue(actual.contains(expected));

        User veryYoungUser = new User("milkyBoss", "1neeDm!lk", 8);
        actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(veryYoungUser);
        }).getMessage();
        assertTrue(actual.contains(expected));

        User ghostUser = new User("neverBorned", "n1dUr$oul", -222);
        actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(ghostUser);
        }).getMessage();
        expected = "Wrong age! You never born,";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("littlguy21", "buYM1acoffe!", null);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "User age can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("lokomoto", null, 28);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Password can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("Kikilast", "lox12", 21);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Password length should be more than 6";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_weakPassword_notOk() {
        User user = new User("IosifSid", "aezakmi27", 29);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Weak password!";
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "okin@w@19", 22);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Login can`t be empty.";
        assertEquals(actual, expected);
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("kia", "Ki@s0rento", 25);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Login length should be more than 6";
        assertTrue(actual.contains(expected));

        User user1 = new User("losb", "12SS$s12", 71);
        actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        }).getMessage();
        assertTrue(actual.contains(expected));

        User user2 = new User("koba", "Kob@337", 63);
        actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user2);
        }).getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_unsupportedSymbolsLogin_notOk() {
        User user = new User("1STKL@ss", "32123sDs@#", 33);
        String actual = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        }).getMessage();
        String expected = "Your login contains unsupported characters.";
        assertTrue(actual.contains(expected));
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }
}
