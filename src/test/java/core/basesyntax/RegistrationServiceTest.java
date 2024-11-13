package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_UserLoginIsAlreadyExist_NotOk() {
        User user = new User();
        user.setLogin("Vitalik");
        user.setAge(62);
        user.setPassword("great_password");
        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin("Vitalik");
        newUser.setAge(43);
        newUser.setPassword("vitalikpassword321");
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
        String excepted = "User with this login is already register!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_UserWithNullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithNullPassword_NotOk() {
        User user = new User();
        user.setLogin("user12345");
        user.setPassword(null);
        user.setAge(41);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        User user = new User();
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_UserLoginLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("abrakadabra");
        user.setAge(83);
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login must be at least 6 characters long!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_UserLoginIsFiveLetters_NotOk() {
        User user = new User();
        user.setLogin("abcdf");
        user.setPassword("messagetotheworld");
        user.setAge(41);
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login must be at least 6 characters long!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_ValidUser_Ok() {
        User user = new User();
        user.setLogin("umaniu");
        user.setAge(18);
        user.setPassword("normalpassword321");
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_EmptyLoginUser_NotOk() {
        User user = new User();
        user.setLogin("");
        user.setAge(32);
        user.setPassword("thebestpassword");
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "User login or password is empty";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void register_UserPasswordIsEmpty_NotOk() {
        User user = new User();
        user.setLogin("Hensai");
        user.setAge(19);
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("Igorenis");
        user.setAge(42);
        user.setPassword("kgr");
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_UserPasswordLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("Umanius");
        user.setAge(53);
        user.setPassword("iakr1");
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_UserLoginIsEightLetters_Ok() {
        User user = new User();
        user.setLogin("Yagrik123");
        user.setAge(53);
        user.setPassword("guaks341");
        User register = registrationService.register(user);
        assertEquals(register, user);
    }

    @Test
    void register_UserAgeLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("Mariana");
        user.setAge(17);
        user.setPassword("kievthebest");
        Exception exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        String excepted = "The user must not be less than 18 years old!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }
}
