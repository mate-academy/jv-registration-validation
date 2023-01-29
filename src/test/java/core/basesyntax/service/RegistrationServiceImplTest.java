package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static List<User> users;
    private static final String DEFAULT_LOGIN = "Login";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final int DEFAULT_AGE = 18;
    private static final String LOGIN_1 = "login1";
    private static final String LOGIN_2 = "login2";
    private static final String LOGIN_3 = "login3";
    private static final int AGE_1 = 100000;
    private static final int AGE_2 = 25;
    private static final int AGE_3 = 999999999;
    private static final int INCORRECT_AGE = 17;
    private static final int NEGATIVE_AGE = -1;
    private static final String INCORRECT_PASSWORD = "12345";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        users = new ArrayList<>();
        users.add(new User(LOGIN_1, DEFAULT_PASSWORD, AGE_1));
        users.add(new User(LOGIN_2, DEFAULT_PASSWORD, AGE_2));
        users.add(new User(LOGIN_3, DEFAULT_PASSWORD, AGE_3));
        users.add(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE));
    }

    @Test
    void register_normalList_ok() {
        List<User> addedUsers = new ArrayList<>();
        for (User user: users) {
            addedUsers.add(registrationService.register(user));
        }
        assertEquals(users, addedUsers);
    }

    @Test
    void register_existingLogin_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(users.get(0));
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_ageUnderRequired_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, INCORRECT_AGE));
        });
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DEFAULT_LOGIN, INCORRECT_PASSWORD, DEFAULT_AGE));
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(null, DEFAULT_PASSWORD, DEFAULT_AGE));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DEFAULT_LOGIN, null, DEFAULT_AGE));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null));
        });
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, NEGATIVE_AGE));
        });
    }
}
