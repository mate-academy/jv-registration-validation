package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.CustomeException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HelloWorldTest1 {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void userWithNullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(22);
        assertThrows(CustomeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOk() {
        User user = new User();
        user.setLogin("user12345");
        user.setPassword(null);
        user.setAge(41);
        assertThrows(CustomeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOk() {
        User user = new User();
        user.setAge(null);
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User login,password or age is null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void threeLettersLogin_NotOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("abrakadabra");
        user.setAge(83);
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User login is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void fiveLettersLogin_NotOk() {
        User user = new User();
        user.setLogin("abcdf");
        user.setPassword("messagetotheworld");
        user.setAge(41);
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User login is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void eightLettersLogin_Ok() {
        User user = new User();
        user.setLogin("Vitaliks");
        user.setAge(22);
        user.setPassword("normalpassword321");
        registrationService.register(user);
        assertTrue("Vitaliks".equals(user.getLogin()));
    }

    @Test
    void emptyloginUser_NotOk() {
        User user = new User();
        user.setLogin("");
        user.setAge(32);
        user.setPassword("thebestpassword");
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User login or password is empty";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void userOk_Ok() {
        User user = new User();
        user.setLogin("Misha4433");
        user.setPassword("lutsk12345");
        user.setAge(41);
        registrationService.register(user);
        assertTrue("Misha4433".equals(user.getLogin()));
    }

    @Test
    void userLoginIsAlreadyExist_NotOk() {
        User user = new User();
        user.setLogin("Vitaliks");
        user.setAge(62);
        user.setPassword("great_password");
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User with this login is already register!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }

    @Test
    void userPasswordIsNotExist_NotOk() {
        User user = new User();
        user.setLogin("Hensai");
        user.setAge(19);
        user.setPassword("");
        assertThrows(CustomeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordHad3letters_NotOk() {
        User user = new User();
        user.setLogin("Igorenis");
        user.setAge(42);
        user.setPassword("kgr");
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void fivePasswordUser_NotOk() {
        User user = new User();
        user.setLogin("Umanius");
        user.setAge(53);
        user.setPassword("iakr1");
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String expected = "User password is need to be at least 6 letters!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void eightPasswordUser_Ok() {
        User user = new User();
        user.setLogin("Yagrik123");
        user.setAge(53);
        user.setPassword("guaks341");
        registrationService.register(user);
        assertTrue("guaks341".equals(user.getPassword()));
    }

    @Test
    void ageUnder18_NotOk() {
        User user = new User();
        user.setLogin("Mariana");
        user.setAge(16);
        user.setPassword("kievthebest");
        Exception exception = assertThrows(CustomeException.class, () ->
                registrationService.register(user));
        String excepted = "User age is under 18!";
        String actual = exception.getMessage();
        assertTrue(actual.contains(excepted));
    }
}
