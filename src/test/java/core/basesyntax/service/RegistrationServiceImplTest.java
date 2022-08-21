package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static User user;
    private static final String VALID_LOGIN = "SomeLogin";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 18;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void user_Ok() {
        assertEquals(service.register(user), user);
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void age_Ok() {
        user.setAge(VALID_AGE);
        assertEquals(service.register(user), user);
    }

    @Test
    void nullValueAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void zeroValueAge_NotOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void negativeValueAge_NotOk() {
        user.setAge(-10);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void smallAge_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void largeAge_Ok() {
        user.setAge(60);
        assertEquals(user, service.register(user));
    }

    @Test
    void maxValueAge_Ok() {
        user.setAge(Integer.MAX_VALUE);
        assertEquals(user, service.register(user));
    }

    @Test
    void password_Ok() {
        user.setPassword(VALID_PASSWORD);
        assertEquals(service.register(user), user);
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void shortPassword_NotOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void notUniqueLogin_NotOk() {
        service.register(user);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }
}
