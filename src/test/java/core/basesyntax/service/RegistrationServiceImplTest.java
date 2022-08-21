package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static User user;
    private static final String VALID_LOGIN = "SomeLogin";
    private static final String VALID_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pass";
    private static final String EMPTY_STRING = "";
    private static final int SMALLEST_VALID_AGE = 18;
    private static final int SMALL_AGE = 10;
    private static final int ZERO_VALUE = 0;
    private static final int NEGATIVE_VALUE = -10;
    private static final int BIGGER_VALID_AGE = 60;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(SMALLEST_VALID_AGE);
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
        user.setAge(SMALLEST_VALID_AGE);
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
        user.setAge(ZERO_VALUE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void negativeValueAge_NotOk() {
        user.setAge(NEGATIVE_VALUE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void smallAge_NotOk() {
        user.setAge(SMALL_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void largeAge_Ok() {
        user.setAge(BIGGER_VALID_AGE);
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
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void emptyPassword_NotOk() {
        user.setPassword(EMPTY_STRING);
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
        user.setLogin(EMPTY_STRING);
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
