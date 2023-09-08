package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_isCorrectAge_notOkay() {
        User user = new User("cat1234", "1234569", 13);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOkay() {
        User user = new User("cat123456", "dodfmr12", 19);
        Storage.PEOPLE.add(user);
        User user2 = new User("cat123456", "cat123456", 20);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUser_ok() {
        User expected = new User("kreep2004", "IloVeJava23", 19);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_loginIsNull_notOkay() {
        User user = new User(null, "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsBlank_notOkay() {
        User user = new User("         ", "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOkay() {
        User user = new User("login", "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOkay() {
        User user = new User("login111", null, 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordsIsBlank_notOkay() {
        User user = new User("login111", "         ", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOkay() {
        User user = new User("login111", "pass", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOkay() {
        User user = null;
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }
}
