package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int VALID_AGE = 20;

    private static final String SHORT_LOGIN = "12345";
    private static final String SHORT_PASSWORD = "12345";
    private static final Integer UNDERAGE = 17;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User("", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginLengthThree_notOk() {
        User user = new User("123", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginLengthFive_notOk() {
        User user = new User("12345", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User(VALID_LOGIN, "", VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordLengthThree_notOk() {
        User user = new User(VALID_LOGIN, "123", VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordLengthFive_notOk() {
        User user = new User(VALID_LOGIN, "12345", VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    void register_underageUsers_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, -1);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationService.register(user1);
        User user2 = new User(VALID_LOGIN, "otherPassword", VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user2));
    }

    @Test
    void register_validUser_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = new User("123456", VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);

    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = new User(VALID_LOGIN, "123456", VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);

    }

    @Test
    void register_ageEighteen_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
