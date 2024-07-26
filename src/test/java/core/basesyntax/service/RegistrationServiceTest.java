package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser;
    private User userWithInvalidLogin;
    private User userWithInvalidPassword;
    private User userWithInvalidAge;

    @BeforeEach
    void beforeEach() {
        validUser = createUser(13652827L, "qazwsxedc82738", 23, "qwdfrw34");
        userWithInvalidLogin = createUser(0L, "bob", 18, "qwdfrw");
        userWithInvalidPassword = createUser(0L, "bobik34", 32, "wefwf");
        userWithInvalidAge = createUser(0L, "borisik", 17, "qwdqwerty");
    }

    @Test
    void register_validUser_registration_OK() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        String existingLogin = "qazwsxedc82738";
        User newUserWithSameLogin = new User();
        newUserWithSameLogin.setLogin(existingLogin);
        newUserWithSameLogin.setAge(34);
        newUserWithSameLogin.setPassword("4574527");
        Storage.people.add(newUserWithSameLogin);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUserWithSameLogin));
    }

    @Test
    void register_loginLengthLessThan6Chars_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void register_passwordLengthLessThan6Chars_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_ageLessThan18_notOk() {
        RegistrationException thrownAgeLessThan18 = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(userWithInvalidAge),
                "Expected at least 18 y.o"
        );

        assertTrue(thrownAgeLessThan18.getMessage().contains("Expected at least 18 y.o"));
    }

    @Test
    void register_UserNull_notOk() {
        User newUser = null;
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_UserLoginIsNull_notOk() {
        validUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_UserAgeIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(null);
        newUser.setPassword("1234567");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_UserPasswordIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(44);
        newUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User createUser(long id, String login, int age, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setAge(age);
        user.setPassword(password);
        return user;
    }
}
