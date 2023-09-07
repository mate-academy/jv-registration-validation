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
        registrationService = null;
    }

    @Test
    void register_isLoginLongEnough_notOkay() {
        User user = new User("cat", "1234567", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isCorrectAge_notOkay() {
        User user1 = new User("cat1234", "1234569", 13);
        User user2 = new User("cat4324", "2341233", 105);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user1));
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_doesUserExist_notOkay() {
        User user = new User("cat123456", "dodfmr12", 19);
        Storage.PEOPLE.add(user);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isLoginNull_notOkay() {
        User user = new User(null, "kjniun123", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_returnCorrectUser_okay() {
        User expected = new User("kreep2004", "IloVeJava23", 19);
        registrationService.register(expected);
        User actual = new User(expected.getLogin(), expected.getPassword(), expected.getAge());
        assertEquals(actual, expected);
    }

    @Test
    void register_isUserNull_notOkay() {
        User user = null;
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkLogin_isLoginCorrect_notOkay() {
        User user0 = new User(null, "ILovEJava33", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user0));
        User user1 = new User("log", "ILovEJava33", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user1));
        User user2 = new User("Loooooooooooogin", "ILovEJava33", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
        User user3 = new User("1234323134132", "ILovEJava33", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user3));
        User user4 = new User(" login?&$1", "ILovEJava33", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user4));
    }

    @Test
    void checkPassword_isPasswordCorrect_notOkay() {
        User user0 = new User("blackcat0", null, 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user0));
        User user1 = new User("blackcat1", "Password", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user1));
        User user2 = new User("blackcat2", "password12345", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
        User user3 = new User("blackcat3", "PASSWORD1234", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user3));
        User user4 = new User("blackcat4", "Pas1", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user4));
        User user5 = new User("blackcat5", "Pas1word1234323442343425243134134", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user5));
        User user6 = new User("blackcat6", "Pas1word1 & ?", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user6));
    }
}
