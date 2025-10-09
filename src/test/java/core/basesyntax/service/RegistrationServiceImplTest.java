package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        Storage.people.add(new User("john_travolta", "123456", 22));
        Storage.people.add(new User("silvester_stallone", "password", 39));
        Storage.people.add(new User("avraam_lincoln", "validpass", 18));
        Storage.people.add(new User("donald_trump", "qwerty", 99));
        Storage.people.add(new User("joe_biden", "freewifi", 70));
    }

    // login existence check
    @Test
    void register_userWithLoginNotExists_ok() {
        User expected = new User("james_gunn", "1234567890", 29);
        registerAndAssertUserExists(expected);
    }

    @Test
    void register_userWithLoginExists_notOk() {
        User existingUser = new User("john_travolta", "123456", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(existingUser));
    }

    // login validation
    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User(null, "123456", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    // password validation
    @Test
    void register_userWithShortLogin_notOk() {
        User userWithShortLogin = new User("lebro", "123456", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithShortLogin));
    }

    @Test
    void register_shortPassword_notOk() {
        User shortPasswordUser = new User("amigo_friend", "lol", 39);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser));
    }

    @Test
    void register_validPassword_Ok() {
        User expected = new User("valid_guy", "six_chars_at_least", 50);
        registerAndAssertUserExists(expected);
    }

    // age validation
    @Test
    void register_smallAge_notOk() {
        User smallAgeUser = new User("johny!", "123456", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(smallAgeUser));
    }

    @Test
    void register_validAge_notOk() {
        User expected = new User("johny!", "123456", 18);
        registerAndAssertUserExists(expected);
    }

    private void registerAndAssertUserExists(User expected) {
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(expected));
    }
}
