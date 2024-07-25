package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidAgeException;
import core.basesyntax.exceptions.InvalidLoginException;
import core.basesyntax.exceptions.InvalidPasswordException;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static User validUser;
    private static User userWithInvalidLogin;
    private static User userWithInvalidPassword;
    private static User userWithInvalidAge;

    @BeforeAll
    static void beforeAll() {
        validUser = createUser(13652827L, "qazwsxedc82738", 23, "qwdfrw34");
        userWithInvalidLogin = createUser(0L, "bob", 18, "qwdfrw");
        userWithInvalidPassword = createUser(0L, "bobik34", 32, "wefwf");
        userWithInvalidAge = createUser(0L, "borisik", 17, "qwdqwerty");
    }

    @Test
    void register_validUser_registration_OK() {
        registrationService.register(validUser);
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        String newLogin = "newLogin";
        User newUser = new User();
        newUser.setLogin(newLogin);
        newUser.setAge(23);
        newUser.setPassword("1234567");
        String existingLogin = "newLogin";
        User newUserWithSameLogin = new User();
        newUserWithSameLogin.setLogin(existingLogin);
        newUserWithSameLogin.setAge(34);
        newUserWithSameLogin.setPassword("4574527");
        registrationService.register(newUser);
        Assertions.assertThrows(UserAlreadyExistException.class,
                () -> registrationService.register(newUserWithSameLogin));
    }

    @Test
    void register_loginLengthLessThan6Chars_notOk() {
        assertThrows(InvalidLoginException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void register_passwordLengthLessThan6Chars_notOk() {
        assertThrows(InvalidPasswordException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_ageLessThan18_notOk() {
        InvalidAgeException thrown = assertThrows(
                InvalidAgeException.class,
                () -> registrationService.register(userWithInvalidAge),
                "Expected at least 18 y.o"
        );

        assertTrue(thrown.getMessage().contains("Expected at least 18 y.o"));
    }

    @Test
    void register_UserNull_notOk() {
        User newUser = null;
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_UserLoginIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setAge(55);
        newUser.setPassword("1234567");
        Assertions.assertThrows(InvalidLoginException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_UserAgeIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(null);
        newUser.setPassword("1234567");
        Assertions.assertThrows(InvalidAgeException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_UserPasswordIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setAge(44);
        newUser.setPassword(null);
        Assertions.assertThrows(InvalidPasswordException.class,
                () -> registrationService.register(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private static User createUser(long id, String login, int age, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setAge(age);
        user.setPassword(password);
        return user;
    }
}
