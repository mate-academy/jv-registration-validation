package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserRegistrationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HelloWorldTest1 {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("Vitalik");
        user.setAge(22);
        user.setPassword("normalpassword321");
        registrationService.register(user);
        assertTrue("Vitalik".equals(user.getLogin()));
    }

    @Test
    void userLoginIsAlreadyExist_NotOk() {
        User user = new User();
        user.setLogin("Vitalik");
        user.setAge(62);
        user.setPassword("great_password");
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User with this login is already register!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void userWithNullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(22);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("user12345");
        user.setPassword(null);
        user.setAge(41);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        User user = new User();
        user.setAge(null);
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login,password or age is null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_ThreeLettersLogin_NotOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("abrakadabra");
        user.setAge(83);
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login must be at least 6 characters long!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_FiveLettersLogin_NotOk() {
        User user = new User();
        user.setLogin("abcdf");
        user.setPassword("messagetotheworld");
        user.setAge(41);
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login must be at least 6 characters long!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_EightLettersLogin_Ok() {
        User user = new User();
        user.setLogin("umaniu23");
        user.setAge(22);
        user.setPassword("normalpassword321");
        registrationService.register(user);
        assertTrue("umaniu23".equals(user.getLogin()));
    }

    @Test
    void register_EmptyLoginUser_NotOk() {
        User user = new User();
        user.setLogin("");
        user.setAge(32);
        user.setPassword("thebestpassword");
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login or password is empty";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void userPasswordIsNotExist_NotOk() {
        User user = new User();
        user.setLogin("Hensai");
        user.setAge(19);
        user.setPassword("");
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidLoginLength_NotOk() {
        User user = new User();
        user.setLogin("Igorenis");
        user.setAge(42);
        user.setPassword("kgr");
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_FivePasswordUser_NotOk() {
        User user = new User();
        user.setLogin("Umanius");
        user.setAge(53);
        user.setPassword("iakr1");
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_EightLoginUser_Ok() {
        User user = new User();
        user.setLogin("Yagrik123");
        user.setAge(53);
        user.setPassword("guaks341");
        registrationService.register(user);
        assertTrue("guaks341".equals(user.getPassword()));
    }

    @Test
    void register_AgeUnder18_NotOk() {
        User user = new User();
        user.setLogin("Mariana");
        user.setAge(17);
        user.setPassword("kievthebest");
        Exception exception = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "The user must not be less than 18 years old!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }
}
