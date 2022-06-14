package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "alice";
    private static final String VALID_PASSWORD = "qwerty";
    private static final int VALID_AGE = 21;
    private static final String INVALID_PASSWORD = "pop";
    private static final int INVALID_AGE = 16;
    private static RegistrationService service;
    private User user;

    @BeforeAll
    public static void initialSetUp() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    public void register_userExist_notOk() {
        User user2 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        service.register(user2);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    public void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(user));

    }

    @Test
    public void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    public void register_loginNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    public void register_allCorrect_ok() {
        assertEquals(service.register(user), user);
    }

    @Nested
    public class ExceptionExpectingTests {
        @Test
        public void register_nullUser_notOk() {
            user = null;
        }
    }
}
