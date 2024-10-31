package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User validUser;
    private User loginIsNull;
    private User shortName;
    private User shortPassword;
    private User passwordIsNull;
    private User ageIsNotEnough;
    private User ageIsNegative;
    private User ageIsNull;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setLogin("tarsan@Gmail.com");
        validUser.setPassword("123456");
        validUser.setAge(30);

        loginIsNull = new User();
        loginIsNull.setId(3L);
        loginIsNull.setLogin(null);
        loginIsNull.setPassword("11223344");
        loginIsNull.setAge(18);

        shortName = new User();
        shortName.setId(4L);
        shortName.setLogin("nazar"); // don't valid name
        shortName.setPassword("11223344");
        shortName.setAge(23);

        shortPassword = new User();
        shortPassword.setId(5L);
        shortPassword.setLogin("nazar@gmail.com");
        shortPassword.setPassword("123"); // don't valid password
        shortPassword.setAge(23);

        passwordIsNull = new User();
        passwordIsNull.setId(6L);
        passwordIsNull.setLogin("nazar@gmail.com");
        passwordIsNull.setPassword(null);
        passwordIsNull.setAge(23);

        ageIsNotEnough = new User();
        ageIsNotEnough.setId(7L);
        ageIsNotEnough.setLogin("Olexiy@gmail.com");
        ageIsNotEnough.setPassword("1234567");
        ageIsNotEnough.setAge(15); // don't valid age

        ageIsNegative = new User();
        ageIsNegative.setId(8L);
        ageIsNegative.setLogin("Mariya@gmail.com");
        ageIsNegative.setPassword("1234567");
        ageIsNegative.setAge(-5);

        ageIsNull = new User();
        ageIsNull.setId(9L);
        ageIsNull.setLogin("Mark@gmail.com");
        ageIsNull.setPassword("1234567");
        ageIsNull.setAge(null);
    }

    @Test
    void register_loginNoExistingUser_addedToStorage() {
        Assertions.assertNull(storageDao.get(validUser.getLogin()),
                "User isn't exist with such login yet");
        registrationService.register(validUser);
        Assertions.assertTrue(Storage.people.contains(validUser),
                "User added in storage with such login");
    }

    @Test
    void register_loginExistingUser_throwsException() {
        Storage.people.add(validUser);
        var exception =
                Assertions.assertThrows(InvalidDataException.class,
                        () -> registrationService.register(validUser));

        String expected = "User already exists "
                + validUser.getLogin()
                + " with such login";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertNotNull(storageDao.get(validUser.getLogin()),
                "User with such login exist in storage.");
    }

    @Test
    void register_nullLogin_throwsException() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(loginIsNull));

        String expected = "User login cannot be null";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertNull(storageDao.get(loginIsNull.getLogin()),
                "User with null login should not be added to storage.");
    }

    @Test
    void userLengthLogin_isOk() {
        var actual = registrationService.register(validUser).getLogin().length();

        Assertions.assertTrue(MIN_LENGTH <= actual);
        Assertions.assertTrue(Storage.people.contains(validUser),
                "User should be added to storage.");
    }

    @Test
    void userLengthLogin_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(shortName));

        var expected = exception.getMessage();
        var actual = "Login must be at least "
                + MIN_LENGTH
                + " current long "
                + shortName.getLogin().length();

        Assertions.assertEquals(actual, expected);
        Assertions.assertFalse(Storage.people.contains(shortName),
                "User shouldn't be added to storage.");
    }

    @Test
    void userPasswordLength_isOk() {
        var actual = registrationService.register(validUser).getPassword().length();

        Assertions.assertTrue(actual >= MIN_LENGTH);
        Assertions.assertTrue(Storage.people.contains(validUser),
                "User should be added to storage.");
    }

    @Test
    void userPasswordLength_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(shortPassword));

        var expected = "Password must be at least"
                + MIN_LENGTH
                + " current length"
                + shortPassword.getPassword().length();
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(shortPassword),
                "User with short password should not be added to storage.");
    }

    @Test
    void userPassword_isNull() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(passwordIsNull));

        var expected = "User password cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(passwordIsNull),
                "User with null password should not be added to storage.");
    }

    @Test
    void userAges_isOk() {
        var actual = registrationService.register(validUser).getAge();

        Assertions.assertTrue(MIN_AGE <= actual);
        Assertions.assertTrue(Storage.people.contains(validUser),
                "User should be added to storage.");
    }

    @Test
    void userAge_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(ageIsNotEnough));

        var expected = "User must be at least "
                + MIN_AGE
                + " years old.";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(ageIsNotEnough),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void userAge_isNegative() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(ageIsNegative));

        var expected = "Age cannot be negative";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(ageIsNegative),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void registerUser_whenAgeIsNull_throwsInvalidDataException() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(ageIsNull));

        var expected = "User age cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(ageIsNull),
                "User with null age should not be added to storage.");
    }
}
