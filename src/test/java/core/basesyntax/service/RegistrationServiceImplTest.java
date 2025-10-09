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

    @Test
    void register_nullUserObject_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUser));
    }

    // login format validation
    @Test
    void register_userWithEmptyLogin_notOk() {
        User userWithEmptyLogin = new User("", "1234567890", 29);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithEmptyLogin));
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User(null, "123456", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_loginLength6Edge_Ok() {
        User expected = new User("123456", "123456", 30);
        registrationService.register(expected);
    }

    @Test
    void register_userWithShortLogin_notOk() {
        User userWithShortLogin = new User("lebro", "123456", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithShortLogin));
    }

    // login existence in database
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

    // password format validation
    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = new User("amigo_friend", null, 39);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User emptyPasswordUser = new User("amigo_friend", "", 39);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyPasswordUser));
    }

    @Test
    void register_passwordLength5Edge_notOk() {
        User passwordLength5User = new User("jackie-chan", "12345", 88);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(passwordLength5User));
    }

    @Test
    void register_passwordLength6Edge_Ok() {
        User passwordLength6User = new User("jackie-chan", "123456", 88);
        registrationService.register(passwordLength6User);
    }

    @Test
    void register_shortPassword_notOk() {
        User shortPasswordUser = new User("amigo_friend", "lol", 39);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser));
    }

    @Test
    void register_validPassword_ok() {
        User expected = new User("valid_guy", "six_chars_at_least", 50);
        registerAndAssertUserExists(expected);
    }

    // age validation
    @Test
    void register_nullAge_notOk() {
        User nullAgeUser = new User("johny!", "123456", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = new User("johny!", "123456", -5);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(negativeAgeUser));
    }

    @Test
    void register_zeroAge_notOk() {
        User zeroAgeUser = new User("johny!", "123456", 0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(zeroAgeUser));
    }

    @Test
    void register_smallAge_notOk() {
        User smallAgeUser = new User("johny!", "123456", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(smallAgeUser));
    }

    @Test
    void register_validAge_ok() {
        User expected = new User("johny!", "123456", 18);
        registerAndAssertUserExists(expected);
    }

    private void registerAndAssertUserExists(User expected) {
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertTrue(Storage.people.contains(expected));
    }
}
