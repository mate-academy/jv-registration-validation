package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_isLoginLongEnough_notOkay() {
        User user = new User("cat", "1234567", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user),
                "Test failed! Your method should throw FailedRegistrationException"
                        + " if login is too short, login: " + user.getLogin()
                        + "\n should be more than 6");
    }

    @Test
    void register_isPasswordLongEnough_notOkay() {
        User user = new User("cat1234", "1234", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user),
                "Test failed! Your method should throw FailedRegistrationException"
                        + " if password is too short, password: " + user.getPassword()
                        + "\n should be more than 6");
    }

    @Test
    void register_isCorrectAge_notOkay() {
        User user1 = new User("cat1234", "1234569", 13);
        User user2 = new User("cat4324", "2341233", 105);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user1),
                "Test failed! Your method should throw FailedRegistrationException"
                        + " if age is too small, age: " + user1.getAge()
                        + "\n should be more than 18");
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2),
                "Test failed! Your method should throw FailedRegistrationException"
                        + " if age is too big, age: " + user2.getAge()
                        + "\n should be less than 100");
    }

    @Test
    void register_doesUserExist_notOkay() {
        StorageDao storageDao = new StorageDaoImpl();
        User user = new User("cat123456", "dodfmr12", 19);
        storageDao.add(user);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user),
                "Test failed! Your method can't register user which already exist.");
    }

    @Test
    void register_isPasswordNull_notOkay() {
        User user = new User("cat123456", null, 19);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isLoginNull_notOkay() {
        User user = new User(null, "kjniun123", 19);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_returnCorrectUser_okay() {
        User expected = new User("kreep2004", "ilovejava", 19);
        registrationService.register(expected);
        User actual = new User(expected.getLogin(), expected.getPassword(), expected.getAge());
        assertEquals(actual, expected, "Test failed! Method shouldn't change the input data!"
                + "Expected: " + expected.toString() + "\n Actual: " + actual.toString());
    }

    @Test
    void register_isUserNull_notOkay() {
        User user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user),
                "Test failed! Method can't take null as user");
    }
}
