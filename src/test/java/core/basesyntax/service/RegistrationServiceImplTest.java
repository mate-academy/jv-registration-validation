package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_PASSWORD = "12345678";
    private static final String CORRECT_LOGIN = "TestLogin";
    private static final Integer CORRECT_AGE = 20;
    private static final int MIN_LOGIN_OR_PASSWORD_LENGTH = 6;
    private static User correctUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = new User();
        correctUser.setLogin(CORRECT_LOGIN);
        correctUser.setAge(CORRECT_AGE);
        correctUser.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword(CORRECT_PASSWORD);
        userWithNullLogin.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin(CORRECT_LOGIN);
        userWithNullPassword.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void register_checkLoginLengthLessThanRequired_notOk() {
        User userWithLoginLength0 = new User();
        userWithLoginLength0.setPassword(CORRECT_PASSWORD);
        userWithLoginLength0.setAge(CORRECT_AGE);
        userWithLoginLength0.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithLoginLength0);
        });
        User testUser = new User();
        testUser.setPassword(CORRECT_PASSWORD);
        testUser.setAge(CORRECT_AGE);
        StringBuilder testLogin = new StringBuilder();
        for (int i = 1; i < MIN_LOGIN_OR_PASSWORD_LENGTH; i++) {
            testLogin.append(i);
            testUser.setLogin(testLogin.toString());
            assertThrows(RegistrationException.class, () -> {
                registrationService.register(testUser);
            });
        }
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_checkLoginLengthEqualOrMoreThanRequired_Ok() {
        User testUser = new User();
        testUser.setPassword(CORRECT_PASSWORD);
        testUser.setAge(CORRECT_AGE);
        testUser.setLogin("123456");
        int expectedStorageSize = Storage.people.size() + 1;
        User actualUser = registrationService.register(testUser);
        assertEquals(testUser, actualUser, "Register user" + actualUser
                + " isn't equal to " + testUser);
        int actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
        testUser = new User();
        testUser.setLogin("01234567890123456789");
        testUser.setPassword(CORRECT_PASSWORD);
        testUser.setAge(CORRECT_AGE);
        expectedStorageSize = Storage.people.size() + 1;
        actualUser = registrationService.register(testUser);
        assertEquals(testUser, actualUser, "Register user" + actualUser
                + " isn't equal to " + testUser);
        actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
    }

    @Test
    void register_userAgeLessThanRequired_notOk() {
        User userWithAge5 = new User();
        userWithAge5.setPassword(CORRECT_PASSWORD);
        userWithAge5.setLogin(CORRECT_LOGIN);
        userWithAge5.setAge(5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithAge5);
        });
        User userWithAge17 = new User();
        userWithAge17.setPassword(CORRECT_PASSWORD);
        userWithAge17.setLogin(CORRECT_LOGIN + "17");
        userWithAge17.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithAge17);
        });
    }

    @Test
    void register_userAgeEqualOrMoreThanRequired_Ok() {
        User userWithAge18 = new User();
        userWithAge18.setPassword(CORRECT_PASSWORD);
        userWithAge18.setAge(18);
        userWithAge18.setLogin(CORRECT_LOGIN);
        int expectedStorageSize = Storage.people.size() + 1;
        User actualUser = registrationService.register(userWithAge18);
        assertEquals(userWithAge18, actualUser,
                "Register user" + actualUser
                + " isn't equal to " + userWithAge18);
        int actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
        User userWithAge40 = new User();
        userWithAge40.setLogin(CORRECT_LOGIN + "40");
        userWithAge40.setPassword(CORRECT_PASSWORD);
        userWithAge40.setAge(40);
        expectedStorageSize = Storage.people.size() + 1;
        actualUser = registrationService.register(userWithAge40);
        assertEquals(userWithAge40, actualUser, "Register user" + actualUser
                + " isn't equal to " + userWithAge40);
        actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
    }

    @Test
    void register_checkPasswordLengthLessThanRequired_notOk() {
        User userWithPasswordLength0 = new User();
        userWithPasswordLength0.setPassword("");
        userWithPasswordLength0.setAge(CORRECT_AGE);
        userWithPasswordLength0.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithPasswordLength0);
        });
        User userWithPasswordLength3 = new User();
        userWithPasswordLength3.setPassword("123");
        userWithPasswordLength3.setAge(CORRECT_AGE);
        userWithPasswordLength3.setLogin(CORRECT_LOGIN + "3");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithPasswordLength3);
        });
        User userWithPasswordLength5 = new User();
        userWithPasswordLength5.setPassword("12345");
        userWithPasswordLength5.setAge(CORRECT_AGE);
        userWithPasswordLength5.setLogin(CORRECT_LOGIN + "5");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithPasswordLength5);
        });
    }

    @Test
    void register_checkPasswordLengthEqualOrMoreRequired_Ok() {
        User testUser = new User();
        testUser.setPassword("123456");
        testUser.setAge(CORRECT_AGE);
        testUser.setLogin(CORRECT_LOGIN);
        int expectedStorageSize = Storage.people.size() + 1;
        User actualUser = registrationService.register(testUser);
        assertEquals(testUser, actualUser, "Register user" + actualUser
                + " isn't equal to " + testUser);
        int actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
        testUser = new User();
        testUser.setLogin(CORRECT_LOGIN + "1");
        testUser.setPassword("01234567890123456789");
        testUser.setAge(CORRECT_AGE);
        expectedStorageSize = Storage.people.size() + 1;
        actualUser = registrationService.register(testUser);
        assertEquals(testUser, actualUser, "Register user" + actualUser
                + " isn't equal to " + testUser);
        actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize,
                "After registration Storage expected size is "
                + expectedStorageSize + " but actual Storage size is " + actualStorageSize);
    }

    @Test
    void register_nullAge_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setLogin(CORRECT_LOGIN);
        userWithNullAge.setPassword(CORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullAge);
        });
    }

    @Test
    void register_isUserWithSameLogin_notOk() {
        Storage.people.add(correctUser);
        User sameCorrectUser = new User();
        sameCorrectUser.setLogin(CORRECT_LOGIN);
        sameCorrectUser.setPassword(CORRECT_PASSWORD);
        sameCorrectUser.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameCorrectUser);
        });
    }
}
