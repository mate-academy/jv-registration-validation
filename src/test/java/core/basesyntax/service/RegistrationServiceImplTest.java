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

    private static User user;
    private static RegistrationServiceImpl registrationService;

    private static final int VALID_AGE = 20;
    private static final String VALID_LOGIN = "some_login";
    private static final String VALID_PASSWORD = "12345678";
    private static final int INVALID_AGE = 15;
    private static final String INVALID_LOGIN = "log";
    private static final String INVALID_PASSWORD = "123";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "Valid user should be registered");
    }

    @Test
    void register_nullValue_NotOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null value shouldn't be registered,"
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with null login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_InvalidAge_NotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setAge(-5);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered,"
                                  + "InvalidDataException should be thrown");
        user.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid age shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_ValidAge_Ok() {
        user.setAge(18);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid age should be registered");
        User user1 = new User();
        user1.setLogin("something interesting");
        user1.setAge(150);
        user1.setPassword(VALID_PASSWORD);
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user), "User with valid age should be registered");
    }

    @Test
    void register_InvalidLogin_NotOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setLogin("login");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid login shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_ValidLogin_Ok() {
        user.setLogin("abcdef");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid login should be registered");
        User user2 = new User();
        user2.setLogin("some loooooooong login");
        user2.setAge(VALID_AGE);
        user2.setPassword(VALID_PASSWORD);
        registrationService.register(user2);
        assertTrue(Storage.people.contains(user), "User with valid login be registered");
    }

    @Test
    void register_InvalidPassword_NotOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
        user.setLogin("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user),
                        "User with invalid password shouldn't be registered, "
                                  + "InvalidDataException should be thrown");
    }

    @Test
    void register_ValidPassword_Ok() {
        user.setPassword("123456");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user), "User with valid password should be registered");
        User user3 = new User();
        user3.setLogin("some login");
        user3.setAge(VALID_AGE);
        user3.setPassword("123456789101112131415");
        registrationService.register(user3);
        assertTrue(Storage.people.contains(user), "User with valid password should be registered");
    }

    @Test
    void register_ExistedUser_NotOk() {
        User user4 = new User();
        user4.setLogin("the same login");
        user4.setAge(VALID_AGE);
        user4.setPassword(VALID_PASSWORD);
        registrationService.register(user4);
        User user5 = new User();
        user5.setLogin("the same login");
        user5.setAge(VALID_AGE);
        user5.setPassword(VALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user5),
                        "Already existed user shouldn't be registered, "
                                + "InvalidDataException should be thrown");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
