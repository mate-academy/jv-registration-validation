package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_minimalAge_Ok() {
        User user = new User("user_with_min_age", PASSWORD, MIN_AGE);
        User actualUser = registrationService.register(user);
        assertEquals(actualUser, user);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_minimalLengthOfPass_Ok() {
        User user = new User("user_with_min_pass_length", "123456", MIN_AGE);
        User actualUser = registrationService.register(user);
        assertEquals(actualUser, user);
    }

    @Test
    void register_spaceLogin_notOk() {
        User user = new User(" ", PASSWORD, MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Invalid data"));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User("", PASSWORD, MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Invalid data"));
    }

    @Test
    void register_passwordWithSpace_notOk() {
        User user = new User(LOGIN, "     d", MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains(PASSWORD));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User(LOGIN, PASSWORD, MIN_AGE);
        User correctUser = registrationService.register(user);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(correctUser),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains(correctUser.getLogin()));
    }

    @Test
    void register_passwordShorterThanRequiredMin_notOk() {
        User user = new User(LOGIN, "12345", MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains(PASSWORD));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(LOGIN, null, MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains(PASSWORD));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, PASSWORD, MIN_AGE);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Invalid data"));
    }

    @Test
    void register_ageLessThanRequiredMin_notOk() {
        User user = new User(LOGIN, PASSWORD, 17);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("age"));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(LOGIN, PASSWORD, null);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("age"));
    }

    @Test
    void register_nullUser_notOk() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(null),
                "Expected register() to throw, but it didn't");
        assertTrue(thrown.getMessage().contains("Invalid data"));
    }
}
