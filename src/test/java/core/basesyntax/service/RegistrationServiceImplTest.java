package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        User firstUser = new User("username1", "password", 18);
        User secondUser = new User("username2", "123456", 38);
        Storage.people.add(firstUser);
        Storage.people.add(secondUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_existingLogin_notOk() {
        User sameLoginFirstUser = new User("username1", "password123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameLoginFirstUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User loginIsFiveChar = new User("abcde", "password123", 34);
        User loginIsTwoChar = new User("ab", "password123", 34);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(loginIsFiveChar);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(loginIsTwoChar);
        });
    }

    @Test
    void register_zeroCharLogin_notOk() {
        User user = new User("", "password123", 34);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User passwordFiveChar = new User("username", "12345", 34);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(passwordFiveChar);
        });
    }

    @Test
    void register_zeroLengthPassword_notOk() {
        User user = new User("username", "", 34);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_youngAge_notOk() {
        User ageTenYears = new User("username", "password", 10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(ageTenYears);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User("username", "password", -5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minimumValidAge_Ok() throws RegistrationException {
        User user = new User("username18", "password", 18);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_overEighteenAge_Ok() throws RegistrationException {
        User user = new User("username", "password", 45);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_loginNull_notOk() {
        User nullLogin = new User(null, "password123", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLogin);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User nullPassword = new User("username", null, 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPassword);
        });
    }

    @Test
    void register_ageNull_notOk() {
        User nullAge = new User("username", "password", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullAge);
        });
    }

    @Test
    void register_validUser_Ok() throws RegistrationException {
        User validUserSixChar = new User("abcdef", "password", 19);
        User validUserEightChar = new User("abcdefgh", "password", 19);
        User registeredUserSixChar = registrationService.register(validUserSixChar);
        User registeredUserEightChar = registrationService.register(validUserEightChar);
        assertEquals(validUserSixChar, registeredUserSixChar);
        assertEquals(validUserEightChar, registeredUserEightChar);
    }

}
