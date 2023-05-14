package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserAlreadyExistException;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User validUser;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User("validlogin", "validpassword", 18);
    }

    @Test
    void registration_userWithValidData_ok() {
        User expected = registrationService.register(validUser);
        assertEquals(expected, validUser, "User should be equals to each other");
    }

    @Test
    void registration_existUser_notOk() {
        registrationService.register(validUser);
        assertThrows(UserAlreadyExistException.class,
                () -> registrationService.register(validUser), "Method should "
                        + "throw UserAlreadyExistException");
    }

    @Test
    void registration_userWithNullInitialization_notOk() {
        User userWithNull = null;
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithNull), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_userWithAllNullFields_notOk() {
        User userWithAllNullFields = new User(null, null, null);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithAllNullFields), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_UserWithInvalidPassword_notOk() {
        User userWithInvalidPassword = new User("123456", "wrong", 20);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithInvalidPassword), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_UserWithNullPassword_notOk() {
        User userWithNullPassword = new User("123456", null, 20);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithNullPassword), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_userWithInvalidLogin_notOk() {
        User userWithInvalidLogin = new User("wrong", "123456", 21);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_userWithNullLogin_notOk() {
        User userWithNullLogin = new User(null, "123456", 21);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithNullLogin), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_userWithInvalidAge_notOk() {
        User userWithInvalidAge = new User("654321", "654321", 15);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithInvalidAge), "Method should "
                        + "throw UserRegistrationException");
    }

    @Test
    void registration_userWithNullAge_notOk() {
        User userWithNullAge = new User("654321", "654321", null);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(userWithNullAge), "Method should "
                        + "throw UserRegistrationException");
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }
}
