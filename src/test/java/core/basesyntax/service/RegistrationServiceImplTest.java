package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User expected = new User(1L, "tarsan@Gmail.com", "123456", 30);
        User actual = registrationService.register(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_lengthUserLogin_ok() {
        User expected = new User(1L, "tarsan@Gmail.com", "123456", 30);
        User actual = registrationService.register(expected);

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(MIN_LENGTH <= expected.getLogin().length());
        Assertions.assertTrue(Storage.people.contains(expected),
                "User should be added to storage.");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        User user = new User(1L, null, "123456", 30);
        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "User login cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user));
    }

    @Test
    void register_lengthUserLogin_notOk() {
        User user = new User(1L, "Bob", "123456", 30);
        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = exception.getMessage();
        var actual = "Login must be at least "
                + MIN_LENGTH
                + " current long "
                + user.getLogin().length();

        Assertions.assertEquals(actual, expected);
        Assertions.assertFalse(Storage.people.contains(user),
                "User shouldn't be added to storage.");
    }

    @Test
    void register_userPassword_isOk() {
        User user = new User(1L, "Andrian", "123456", 30);

        var actual = registrationService.register(user).getPassword().length();

        Assertions.assertTrue(actual >= MIN_LENGTH);
        Assertions.assertTrue(Storage.people.contains(user),
                "User should be added to storage.");
    }

    @Test
    void register_userPasswordLength_notOk() {
        User user = new User(1L, "Andrian", "123", 30);

        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "Password must be at least"
                + MIN_LENGTH
                + " current length"
                + user.getPassword().length();
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user),
                "User with short password should not be added to storage.");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        User user = new User(1L, "Andrian", null, 30);

        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "User password cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user),
                "User with null password should not be added to storage.");
    }

    @Test
    void register_userAges_isOk() {
        User user = new User(1L, "Andrian", "123456", 30);

        var actual = registrationService.register(user).getAge();

        Assertions.assertTrue(MIN_AGE <= actual);
        Assertions.assertTrue(Storage.people.contains(user),
                "User should be added to storage.");
    }

    @Test
    void register_userAge_isNotOk() {
        User user = new User(1L, "Andrian", "123456", 17);

        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "User must be at least "
                + MIN_AGE
                + " years old.";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        User user = new User(1L, "Andrian", "123456", -17);

        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "Age cannot be negative";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        User user = new User(1L, "Andrian", "123456", null);

        var exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));

        var expected = "User age cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(user),
                "User with null age should not be added to storage.");
    }
}
