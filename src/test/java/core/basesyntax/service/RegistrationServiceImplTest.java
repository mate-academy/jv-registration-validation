package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final class RegistrationServiceTestConstants {
        private static final int DEFAULT_VALID_AGE = 20;
        private static final int MIN_VALID_AGE = 18;
        private static final int BIG_VALID_AGE = 150;
        private static final String DEFAULT_VALID_LOGIN = "some_login";
        private static final String MIN_VALID_LOGIN = "abcdef";
        private static final String BIG_VALID_LOGIN = "some loooooooooong login";
        private static final String DEFAULT_VALID_PASSWORD = "12345678";
        private static final String MIN_VALID_PASSWORD = "123456";
        private static final String BIG_VALID_PASSWORD = "12345678910111213114";
        private static final int INVALID_AGE = 15;
        private static final int INVALID_NEGATIVE_AGE = -5;
        private static final int INVALID_ZERO_AGE = 0;
        private static final int INVALID_AGE_MAX = 17;
        private static final String INVALID_LOGIN = "log";
        private static final String EMPTY_INVALID_LOGIN = "";
        private static final String INVALID_LOGIN_MAX = "login";
        private static final String FIRST_USER_LOGIN = "something";
        private static final String THIRD_USER_LOGIN = "something interesting";
        private static final String INVALID_PASSWORD = "123";
        private static final String EMPTY_INVALID_PASSWORD = "";
        private static final String INVALID_PASSWORD_MAX = "12345";
    }

    private static User user;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(RegistrationServiceTestConstants.DEFAULT_VALID_AGE);
        user.setLogin(RegistrationServiceTestConstants.DEFAULT_VALID_LOGIN);
        user.setPassword(RegistrationServiceTestConstants.DEFAULT_VALID_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        Storage.people.add(user);
        assertTrue(Storage.people.contains(user), "Valid user should be registered");
    }

    @Test
    void register_nullValue_notOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null value shouldn't be registered,"
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(RegistrationServiceTestConstants.INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setAge(RegistrationServiceTestConstants.INVALID_NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered,"
                                  + "InvalidDataException should be thrown");
        user.setAge(RegistrationServiceTestConstants.INVALID_ZERO_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setAge(RegistrationServiceTestConstants.INVALID_AGE_MAX);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_validAge_ok() {
        user.setAge(RegistrationServiceTestConstants.MIN_VALID_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid age should be registered");
        User user1 = new User();
        user1.setLogin(RegistrationServiceTestConstants.FIRST_USER_LOGIN);
        user1.setAge(RegistrationServiceTestConstants.BIG_VALID_AGE);
        user1.setPassword(RegistrationServiceTestConstants.DEFAULT_VALID_PASSWORD);
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user1), "User with valid age should be registered");
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(RegistrationServiceTestConstants.INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setLogin(RegistrationServiceTestConstants.EMPTY_INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setLogin(RegistrationServiceTestConstants.INVALID_LOGIN_MAX);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_validLogin_ok() {
        user.setLogin(RegistrationServiceTestConstants.MIN_VALID_LOGIN);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid login should be registered");
        User user2 = new User();
        user2.setLogin(RegistrationServiceTestConstants.BIG_VALID_LOGIN);
        user2.setAge(RegistrationServiceTestConstants.DEFAULT_VALID_AGE);
        user2.setPassword(RegistrationServiceTestConstants.DEFAULT_VALID_PASSWORD);
        registrationService.register(user2);
        assertTrue(Storage.people.contains(user2), "User with valid login be registered");
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(RegistrationServiceTestConstants.INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setPassword(RegistrationServiceTestConstants.EMPTY_INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setPassword(RegistrationServiceTestConstants.INVALID_PASSWORD_MAX);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_validPassword_ok() {
        user.setPassword(RegistrationServiceTestConstants.MIN_VALID_PASSWORD);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid password should be registered");
        User user3 = new User();
        user3.setLogin(RegistrationServiceTestConstants.THIRD_USER_LOGIN);
        user3.setAge(RegistrationServiceTestConstants.DEFAULT_VALID_AGE);
        user3.setPassword(RegistrationServiceTestConstants.BIG_VALID_PASSWORD);
        registrationService.register(user3);
        assertTrue(Storage.people.contains(user3), "User with valid password should be registered");
    }

    @Test
    void register_existedUser_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "Already existed user shouldn't be registered, "
                                + "InvalidDataException should be thrown");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
