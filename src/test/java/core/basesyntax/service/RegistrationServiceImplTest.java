package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final User USER_VALID = new User("Badcannon", "password1", 39);
    private static final User USER_WITH_SHORT_PASS = new User("Sam", "12345", 39);
    private static final User USER_WITH_EMPTY_LOGIN = new User("", "123456", 39);
    private static final User USER_WITH_NULL_LOGIN = new User("", "123456", 39);
    private static final User USER_AGE_LESS_18 = new User("John_junior", "password4", 15);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_validUser_OK() {
        User actual = registrationService.register(USER_VALID);
        assertTrue(people.contains(actual));
    }

    @Test
    void register_userWithAgeLess18_NotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(USER_AGE_LESS_18);
        });
    }

    @Test
    void register_userWithPassLessThanSixSymbol_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(USER_WITH_SHORT_PASS);
        });
    }

    @Test
    void register_userWithEmptyLogin_notOk() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(USER_WITH_EMPTY_LOGIN),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Login is empty"));
    }

    @Test
    void register_userWithNullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(USER_WITH_NULL_LOGIN);
        });
    }

    @Test
    void register_userWithSameLogin_NotOK() {
        User actual = registrationService.register(USER_VALID);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(actual);
        });
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }
}
