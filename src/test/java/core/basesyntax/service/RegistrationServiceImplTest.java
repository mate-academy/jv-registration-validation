package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User TOO_YOUNG = new User(16, "Bob", "passwd");
    private static final User USER_OK = new User(25, "Alice", "passwd");
    private static final User DUPLICATE_LOGIN = new User(23, "Alice", "passwd");
    private static final User TOO_OLD = new User(150, "Mystery", "passwd");
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullObject_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_duplicateLogin_notOk() {
        registrationService.register(new User(USER_OK));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DUPLICATE_LOGIN));
        });
    }

    @Test
    void register_doubleRegistration_notOk() {
        registrationService.register(new User(USER_OK));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(USER_OK));
        });
    }

    @Test
    void register_weekPassword_notOk() {
        User testUser = new User(USER_OK);
        testUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword("passwd");
        assertEquals(USER_OK, registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(19, null, "passwd"));
        });
    }

    @Test
    void register_nullPasswd_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(19, "SomeUser", null));
        });
    }

    @Test
    void register_youngUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(TOO_YOUNG));
        });
    }

    @Test
    void register_fakeAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(TOO_OLD));
        });
    }

    @Test
    void register_oneUser_ok() {
        User actual = registrationService.register(new User(USER_OK));
        assertEquals(USER_OK, actual);
    }

    @Test
    void register_SeveralUsers_Ok() {
        User testUser = new User(USER_OK);
        User actual = registrationService.register(new User(testUser));
        assertEquals(testUser, actual);
        testUser.setLogin(USER_OK.getLogin() + 1);
        actual = registrationService.register(new User(testUser));
        assertEquals(testUser, actual);
    }
}
