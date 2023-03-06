package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final String VALID_NAME = "Ivan Zhuravlev";
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "my password 123";
    private static final int INVALID_NEGATIVE_AGE = -10;
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_user_ok() {
        User actual = registration.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_several_users_ok() {
        registration.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
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
        user.setAge(VALID_AGE - 1);
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

