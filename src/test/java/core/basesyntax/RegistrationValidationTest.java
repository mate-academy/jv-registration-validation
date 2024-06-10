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
    private User testUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_existingUser_notOk() {
        validUser();
        Storage.people.add(validUser());
        testUser.setLogin(validUser().getLogin());
        testUser.setPassword(validUser().getPassword());
        testUser.setAge(validUser().getAge());
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser()));
    }

    @Test
    void register_validUser_Ok() {
        validUser();
        User actual = registrationService.register(validUser());
        assertEquals(actual, validUser());
    }

    @Test
    void register_invalidUser_notOk() {
        testUser.setLogin(validUser().getLogin());
        testUser.setPassword("12345");
        testUser.setAge(validUser().getAge());
        Storage.people.add(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void setLogin_lessThanSixCharacters_notOk() {
        testUser.setLogin("user");
        String login = testUser.getLogin();
        assertFalse(login.length() >= 6, "Login should be at least 6 characters");
    }

    @Test
    void setPassword_lessThanSixCharacters_notOk() {
        testUser.setPassword("1111");
        String password = testUser.getPassword();
        assertFalse(password.length() >= 6, "Password should be at least 6 characters");
    }

    @Test
    void setAge_lessThanEighteen_notOk() {
        testUser.setAge(6);
        assertFalse(testUser.getAge() >= 18, "Age should be at least 18");
    }

    @Test
    void setLogin_null_notOk() {
        testUser.setLogin(null);
        String login = testUser.getLogin();
        assertNull(login, "Login should not be null");
    }

    @Test
    void setPassword_null_notOk() {
        testUser.setPassword(null);
        String password = testUser.getPassword();
        assertNull(password, "Password should not be null");
    }

    @Test
    void setAge_null_notOk() {
        testUser.setAge(null);
        assertNull(testUser.getAge(), "Age should not be null");
    }

    User validUser() {
        testUser.setLogin("qwerty");
        testUser.setPassword("123456");
        testUser.setAge(18);
        return testUser;
    }
}
