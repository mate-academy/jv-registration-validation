package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_existingUser_notOk() {
        User user = new User();
        user.setLogin("user69");
        user.setPassword("qwerty");
        user.setAge(19);
        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin("user69");
        newUser.setPassword("qwerty");
        newUser.setAge(19);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_validUser_Ok() {
        User validUser = new User();
        validUser.setLogin("valid_login");
        validUser.setPassword("valid_password");
        validUser.setAge(18);
        User actual = registrationService.register(validUser);
        assertEquals(actual, validUser);
    }

    @Test
    void register_invalidUser_notOk() {
        User invalidLoginUser = new User();
        invalidLoginUser.setLogin("bob");
        invalidLoginUser.setPassword("qwerty");
        invalidLoginUser.setAge(33);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(invalidLoginUser));
        User invalidPasswordUser = new User();
        invalidPasswordUser.setLogin("robert");
        invalidPasswordUser.setPassword("12345");
        invalidPasswordUser.setAge(19);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(invalidPasswordUser));
        User invalidAgeUser = new User();
        invalidAgeUser.setLogin("antony");
        invalidAgeUser.setPassword("123456");
        invalidAgeUser.setAge(7);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(invalidAgeUser));
    }

    @Test
    void register_loginLessThanSixCharacters_notOk() {
        User user = new User();
        user.setLogin("user");
        String login = user.getLogin();
        assertFalse(login.length() >= 6, "Login should be at least 6 characters");
    }

    @Test
    void register_passwordLessThanSixCharacters_notOk() {
        User user = new User();
        user.setPassword("1111");
        String password = user.getPassword();
        assertFalse(password.length() >= 6, "Password should be at least 6 characters");
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        User user = new User();
        user.setAge(6);
        assertFalse(user.getAge() >= 18, "Age should be at least 18");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        String login = user.getLogin();
        assertNull(login, "Login should not be null");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        String password = user.getPassword();
        assertNull(password, "Password should not be null");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        assertNull(user.getAge(), "Age should not be null");
    }
}
