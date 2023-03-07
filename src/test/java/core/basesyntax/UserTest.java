package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static final String VALID_NAME = "Ivan Zhuravlev";
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "my password 123";
    private static final int INVALID_NEGATIVE_AGE = -10;
    private static final String INVALID_SHORT_PASSWORD = "12345";
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
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
        assertEquals(Storage.people.get(0), actual);
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
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void little_age_notOk() {
        user.setAge(VALID_AGE - 1);
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void short_password_notOk() {
        user.setPassword(INVALID_SHORT_PASSWORD);
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void add_user_with_existing_login_notOk() {
        registration.register(user);
        assertThrows(InvalidDataException.class, () -> {
            registration.register(user);
        });
    }
}

