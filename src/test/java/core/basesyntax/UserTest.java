package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final String VALID_NAME = "Ivan Zhuravlev";
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "my password 123";
    private static final int INVALID_NEGATIVE_AGE = -10;
    private static final int INVALID_AGE = 15;
    private static final String INVALID_SHORT_PASSWORD = "12345";
    private RegistrationServiceImpl registration;
    private User user;

    @BeforeEach
    void setUp() {
        registration = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(VALID_NAME);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_user_ok() {
        User actual = registration.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_several_users_ok() {
        int quantity = 50;
        for (int i = 0; i < quantity; i++) {
            user = new User();
            user.setAge(VALID_AGE);
            user.setPassword(VALID_PASSWORD);
            user.setLogin("testName" + i);
            registration.register(user);
            assertTrue(Storage.people.contains(user));
        }
        assertEquals(quantity, Storage.people.size());
    }

    @Test
    void object_user_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void login_null_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void password_null_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void age_null_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void invalid_age_notOk() {
        user.setAge(INVALID_NEGATIVE_AGE);
        User actual = registration.register(user);
        assertNull(actual);
    }

    @Test
    void little_age_notOk() {
        user.setAge(INVALID_AGE);
        User actual = registration.register(user);
        assertNull(actual);
    }

    @Test
    void short_password_notOk() {
        user.setPassword(INVALID_SHORT_PASSWORD);
        User actual = registration.register(user);
        assertNull(actual);
    }
}

