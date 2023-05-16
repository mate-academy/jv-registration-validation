package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_USER_AMOUNT = 20;
    private static final int USERS_TO_ADD = 3;
    private static final String SHORT_STRING = "abcde";
    private static final int YOUNG_AGE = 10;
    private static RegistrationService registrationService;
    private static Stack<User> users;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        users = new Stack<>();
        for (int i = 0; i < DEFAULT_USER_AMOUNT; i++) {
            User user = new User();
            user.setLogin("user-login-" + i);
            user.setPassword("user-password-" + i);
            user.setAge(18 + i);
            users.add(user);
        }
    }

    @Test
    void register_multipleUsers_Ok() {
        List<User> registeredUsers = new ArrayList<>();
        for (int i = 0; i < USERS_TO_ADD; i++) {
            User registeredUser = registrationService.register(users.pop());
            registeredUsers.add(registeredUser);
        }
        assertEquals(USERS_TO_ADD, registeredUsers.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserException.class, () -> {
            User actual = registrationService.register(null);
        });
    }

    @Test
    void register_returnsUserOnSuccess_Ok() {
        User actual = registrationService.register(users.pop());
        assertEquals(User.class, actual.getClass());
    }

    @Test
    void register_returnedUserHasId_Ok() {
        User actual = registrationService.register(users.pop());
        assertNotNull(actual.getId());
    }

    @Test
    void register_existingUser_notOk() {
        User user = users.pop();
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = users.pop();
        user.setLogin(SHORT_STRING);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = users.pop();
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = users.pop();
        user.setPassword(SHORT_STRING);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = users.pop();
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_youngUser_notOk() {
        User user = users.pop();
        user.setAge(YOUNG_AGE);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = users.pop();
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }
}
