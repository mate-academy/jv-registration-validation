package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
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
    void register_validUser_Ok() {
        User validUser = new User("Mickie", "123456", 18);
        User actual1 = registrationService.register(validUser);
        assertTrue(Storage.people.contains(actual1));
        User validUser2 = new User("Mouse", "123456", 30);
        User actual2 = registrationService.register(validUser2);
        assertTrue(Storage.people.contains(actual2));
    }

    @Test
    void register_invalidAge_NotOk() {
        User invalidUserAge1 = new User("Maxim", "123456", 17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUserAge1);
        }, "Should throw an Exception: \"The user should be at least 18 years old.\"");
        User invalidUserAge2 = new User("Max", "123456", -19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUserAge2);
        }, "Should throw an Exception: \"The user should be at least 18 years old.\"");
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidUserPassword = new User("Mocko", "12345", 20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(invalidUserPassword);
        }, "Should throw an Exception: \"Password should be at least 6 characters.\"");
    }

    @Test
    void register_theSameUserLogin_notOk() {
        User theSameUserLogin = new User("Month", "1234567", 20);
        registrationService.register(theSameUserLogin);
        User theSameUserLogin2 = new User("Month", "123456", 19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(theSameUserLogin2);
        }, "Should throw an Exception: \"The user with such login already exists. "
                + "Please, enter another login.\n\"");
    }

    @Test
    void register_nullAge_NotOk() {
        User nullAgeUser = new User("Stew", "123456", null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(nullAgeUser);
        }, "Should throw NullPointerException.");
    }

    @Test
    void register_nullPassword_NotOk() {
        User nullUserPassword = new User("Steins", null, 18);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(nullUserPassword);
        }, "Should throw NullPointerException.");
    }

    @Test
    void register_nullLogin_NotOk() {
        User nullUserLogin = new User(null, "123456", 18);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(nullUserLogin);
        }, "Should throw NullPointerException.");
    }
}
