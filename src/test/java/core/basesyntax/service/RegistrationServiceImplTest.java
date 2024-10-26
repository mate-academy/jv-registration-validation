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
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;
    private User fifthUser;
    private User sixthUser;
    private User seventhUser;
    private User eighthUser;
    private User nineUser;

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
        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setLogin("tarsan@Gmail.com");
        firstUser.setPassword("123456");
        firstUser.setAge(30);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setLogin("nazar@gmail.com");
        secondUser.setPassword("123456");
        secondUser.setAge(25);

        thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setLogin(null);
        thirdUser.setPassword("11223344");
        thirdUser.setAge(18);

        fourthUser = new User();
        fourthUser.setId(4L);
        fourthUser.setLogin("nazar");
        fourthUser.setPassword("11223344");
        fourthUser.setAge(23);

        fifthUser = new User();
        fifthUser.setId(5L);
        fifthUser.setLogin("nazar@gmail.com");
        fifthUser.setPassword("123");
        fifthUser.setAge(23);

        sixthUser = new User();
        sixthUser.setId(6L);
        sixthUser.setLogin("nazar@gmail.com");
        sixthUser.setPassword(null);
        sixthUser.setAge(23);

        seventhUser = new User();
        seventhUser.setId(7L);
        seventhUser.setLogin("Olexiy@gmail.com");
        seventhUser.setPassword("1234567");
        seventhUser.setAge(15);

        eighthUser = new User();
        eighthUser.setId(8L);
        eighthUser.setLogin("Mariya@gmail.com");
        eighthUser.setPassword("1234567");
        eighthUser.setAge(-5);

        nineUser = new User();
        nineUser.setId(9L);
        nineUser.setLogin("Mark@gmail.com");
        nineUser.setPassword("1234567");
        nineUser.setAge(null);
    }

    @Test
    void register_loginNoExistingUser_addedToStorage() {
        Assertions.assertNull(storageDao.get(firstUser.getLogin()),
                "User isn't exist with such login yet");
        registrationService.register(firstUser);
        Assertions.assertTrue(Storage.people.contains(firstUser),
                "User added in storage with such login");
    }

    @Test
    void register_loginExistingUser_throwsException() {
        Storage.people.add(secondUser);
        var exception =
                Assertions.assertThrows(InvalidDataException.class,
                        () -> registrationService.register(secondUser));

        String expected = "User already exists "
                + secondUser.getLogin()
                + " with such login";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertNotNull(storageDao.get(secondUser.getLogin()),
                "User with such login exist in storage.");
    }

    @Test
    void register_nullLogin_throwsException() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(thirdUser));

        String expected = "User login cannot be null";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertNull(storageDao.get(thirdUser.getLogin()),
                "User with null login should not be added to storage.");
    }

    @Test
    void userLengthLogin_isOk() {
        var actual = registrationService.register(firstUser).getLogin().length();

        Assertions.assertTrue(MIN_LENGTH <= actual);
        Assertions.assertTrue(Storage.people.contains(firstUser),
                "User should be added to storage.");
    }

    @Test
    void userLengthLogin_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(fourthUser));

        var expected = exception.getMessage();
        var actual = "Login must be at least "
                + MIN_LENGTH
                + " current long "
                + fourthUser.getLogin().length();

        Assertions.assertEquals(actual, expected);
        Assertions.assertFalse(Storage.people.contains(fourthUser),
                "User shouldn't be added to storage.");
    }

    @Test
    void userPasswordLength_isOk() {
        var actual = registrationService.register(firstUser).getLogin().length();

        Assertions.assertTrue(MIN_LENGTH <= actual);
        Assertions.assertTrue(Storage.people.contains(firstUser),
                "User should be added to storage.");
    }

    @Test
    void userPasswordLength_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(fifthUser));

        var expected = "Password must be at least"
                + MIN_LENGTH
                + " current length"
                + fifthUser.getPassword().length();
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(fifthUser),
                "User with short password should not be added to storage.");
    }

    @Test
    void userPassword_isNull() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(sixthUser));

        var expected = "User password cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(sixthUser),
                "User with null password should not be added to storage.");
    }

    @Test
    void userAges_isOk() {
        var actual = registrationService.register(firstUser).getAge();

        Assertions.assertTrue(MIN_AGE <= actual);
        Assertions.assertTrue(Storage.people.contains(firstUser),
                "User should be added to storage.");
    }

    @Test
    void userAge_isNotOk() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(seventhUser));

        var expected = "User must be at least "
                + MIN_AGE
                + " years old.";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(seventhUser),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void userAge_isNegative() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(eighthUser));

        var expected = "Age cannot be negative";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(eighthUser),
                "User with incorrect age should not be added to storage.");
    }

    @Test
    void userAge_isNull() {
        var exception = Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(nineUser));

        var expected = "User age cannot be null";
        var actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Storage.people.contains(nineUser),
                "User with null age should not be added to storage.");
    }
}
