package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NotValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userWithInvalidPassword;
    private static User userWithInvalidLogin;
    private static User userWithInvalidAge;
    private static User secondValidUser;
    private static User userWithNull;
    private static User validUser;

    @BeforeAll
    public static void setUp() {
        userWithInvalidPassword = new User();
        userWithInvalidPassword.setLogin("123456");
        userWithInvalidPassword.setPassword("wrond");
        userWithInvalidPassword.setAge(20);

        userWithInvalidAge = new User();
        userWithInvalidAge.setLogin("654321");
        userWithInvalidAge.setPassword("654321");
        userWithInvalidAge.setAge(15);

        userWithInvalidLogin = new User();
        userWithInvalidLogin.setLogin("wrong");
        userWithInvalidLogin.setPassword("123456");
        userWithInvalidLogin.setAge(21);

        userWithNull = null;

        validUser = new User();
        validUser.setLogin("validlogin");
        validUser.setPassword("validpassword");
        validUser.setAge(18);

        secondValidUser = new User();
        secondValidUser.setLogin("qwerty");
        secondValidUser.setPassword("098876");
        secondValidUser.setAge(30);

        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registrationUserWithValidData_Ok() {
        User expected = registrationService.register(validUser);
        assertEquals(expected, validUser, "User should be equals to each other");
        expected = registrationService.register(secondValidUser);
        assertEquals(expected, secondValidUser, "User should be equals to each other");
    }

    @Test
    void registrationUserWithNullInitializationShouldThrow_NotValidDataExeption_Ok() {
        assertThrows(NotValidDataException.class,
                () -> registrationService.register(userWithNull), "Method should "
                        + "throw NotValitDataExeption");
    }

    @Test
    void registrationUserWithInvalidPasswordShouldThrow_NotValidData_Ok() {
        assertThrows(NotValidDataException.class,
                () -> registrationService.register(userWithInvalidPassword), "Method should "
                        + "throw NotValitDataExeption");
    }

    @Test
    void registrationUserWithInvalidLoginThrow_NotValidData_Ok() {
        assertThrows(NotValidDataException.class,
                () -> registrationService.register(userWithInvalidLogin), "Method should "
                        + "throw NotValitDataExeption");
    }

    @Test
    void registrationUserWithinInvalidAgeThrow_NotValidData_Ok() {
        assertThrows(NotValidDataException.class,
                () -> registrationService.register(userWithInvalidAge), "Method should "
                        + "throw NotValitDataExeption");
    }

    @Test
    void getUserFromStorage_Ok() {
        User expected = registrationService.register(secondValidUser);
        assertEquals(expected, secondValidUser, "method should return created user from storage");
    }
}
