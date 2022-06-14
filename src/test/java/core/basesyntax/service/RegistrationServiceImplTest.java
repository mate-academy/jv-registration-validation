package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private final static User TOO_YOUNG = new User(16, "Bob", "passwd");
    private final static User USER_OK = new User(25, "Alice", "passwd");
    private final static User DUPLICATE_LOGIN = new User(23, "Alice", "passwd");
    private final static User TOO_OLD = new User(150, "Mystery", "passwd");

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_NullObject_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_DuplicateLogin_NotOk() {
        registrationService.register(new User(USER_OK));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DUPLICATE_LOGIN));
        });
    }

    @Test
    void register_DoubleRegistration_NotOk() {
        registrationService.register(new User(USER_OK));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(USER_OK));
        });
    }

    @Test
    void register_WeekPassword_NotOk() {
        User testUser = new User(USER_OK);
        testUser.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword("passwd");
        assertEquals(USER_OK, registrationService.register(testUser));
    }

    @Test
    void register_NullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(19, null, "passwd"));
        });
    }

    @Test
    void register_NullPasswd_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(19, "SomeUser", null));
        });
    }

    @Test
    void register_YoungUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(TOO_YOUNG));
        });
    }

    @Test
    void register_FakeAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(TOO_OLD));
        });
    }

    @Test
    void register_OneUser_Ok() {
        User actual = registrationService.register(new User(USER_OK));
        assertEquals(USER_OK, actual);
    }

    @Test
    void register_SeveralUsers_Ok() {
        User testUser = new User(USER_OK);
        for (int i = 0; i < 50; i++) {
            User actual = registrationService.register(new User(testUser));
            assertEquals(testUser, actual);
            testUser.setLogin(USER_OK.getLogin() + i);
            testUser.setAge(testUser.getAge() + 1);
        }
    }
}