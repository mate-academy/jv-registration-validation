package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_isLoginLongEnough_notOkay() {
        User user = new User("cat", "1234567", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isCorrectAge_notOkay() {
        User user1 = new User("cat1234", "1234569", 13);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_doesUserExist_notOkay() {
        User user = new User("cat123456", "dodfmr12", 19);
        Storage.PEOPLE.add(user);
        User user2 = new User("cat123456", "cat123456", 20);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_isLoginNull_notOkay() {
        User user = new User(null, "kjniun123", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_returnCorrectUser_okay() {
        User expected = new User("kreep2004", "IloVeJava23", 19);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void checkLogin_isLoginNull_notOkay() {
        User user = new User(null, "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkLogin_isBlank_notOkay() {
        User user = new User("         ", "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkLogin_isTooShort_notOkay() {
        User user = new User("login", "password1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkPassword_isLoginNull_notOkay() {
        User user = new User("login111", null, 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkPassword_isBlank_notOkay() {
        User user = new User("login111", "         ", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkPassword_isTooShort_notOkay() {
        User user = new User("login111", "pass", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }
}
