package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void addNewUser_Ok() {
        User expected = new User();
        expected.setLogin("admin98@gmail.com");
        expected.setPassword("Admin123");
        expected.setAge(23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void addNullUser_NotOk() {
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(null));
    }

    @Test
    void addNullPassword_NotOk() {
        User expected = new User();
        expected.setLogin("admin@gmail.com");
        expected.setPassword(null);
        expected.setAge(23);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(expected),
                "Password cannot be empty");

    }

    @Test
    void addShortPassword_NotOk() {
        User expected = new User();
        expected.setLogin("admin@gmail.com");
        expected.setPassword("Admi");
        expected.setAge(23);
        assertThrows(RegistrationUserException.class, () ->
                registrationService.register(expected),
                "Password cannot be less than 6 characters");
    }

    @Test
    void addNullLogin_NotOk() {
        User expected = new User();
        expected.setLogin(null);
        expected.setPassword("Admin123");
        expected.setAge(23);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(expected),
                "Login cant be null");
    }

    @Test
    void addShortLogin_NotOk() {
        User expected = new User();
        expected.setLogin("admin");
        expected.setPassword("Admin123");
        expected.setAge(23);
        assertThrows(RegistrationUserException.class, () ->
                registrationService.register(expected),
                "Login cant be less than 6 characters");
    }

    @Test
    void invalidAge_NotOk() {
        User expected = new User();
        expected.setLogin("admin@gmail.com");
        expected.setPassword("Admin123");
        expected.setAge(17);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(expected),
                "Age must be at least 18 years old.");
    }

    @Test
    void addRegisteredUser_NotOk() {
        User expected = new User();
        expected.setLogin("admin@gmail.com");
        expected.setPassword("Admin123");
        expected.setAge(23);
        registrationService.register(expected);

        User registerUser = new User();
        registerUser.setLogin("admin@gmail.com");
        registerUser.setPassword("Admin874");
        registerUser.setAge(26);

        assertThrows(RegistrationUserException.class, () ->
                registrationService.register(registerUser),
                "User with this login already exists.");
    }

    @Test
    void addEmptyLogin_NotOk() {
        User expected = new User();
        expected.setLogin("");
        expected.setPassword("Admin874");
        expected.setAge(26);
        assertThrows(RegistrationUserException.class, () ->
                registrationService.register(expected),
                "Login can't be null and must be at least 6 characters long.");
    }

    @Test
    void addEmptyPassword_NotOk() {
        User expected = new User();
        expected.setLogin("Liza@gmail.com");
        expected.setPassword("");
        expected.setAge(27);
        assertThrows(RegistrationUserException.class, () ->
                registrationService.register(expected),
                "Password can't be null and must"
                        + "be at least 6 characters long.");
    }

}
