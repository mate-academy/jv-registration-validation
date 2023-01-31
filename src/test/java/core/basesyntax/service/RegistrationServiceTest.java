package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.PasswordException;
import core.basesyntax.service.exceptions.UserExistException;
import core.basesyntax.service.exceptions.UserNullException;
import core.basesyntax.service.exceptions.UserYoungException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

    private static final int MIN_USER_AGE = 18;
    private static final String VALID_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "login";
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_normalUser_ok() {
        User normalUser = createUser(DEFAULT_LOGIN, VALID_PASSWORD, MIN_USER_AGE);
        User registeredUser = registrationService.register(normalUser);

        Assertions.assertSame(normalUser, registeredUser,
                "register method must return same user as was supplied");
        assertNotNull(normalUser.getId(), "User must have id after registration");
        // check db state
        List<User> expectedDbState = new ArrayList<>();
        expectedDbState.add(normalUser);
        assertEquals(Storage.people, expectedDbState, "User must be saved in db");
    }

    @Test
    void register_fewNormalUsers_ok() {
        List<User> expectedDbState = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User normalUser = createUser(DEFAULT_LOGIN + i, VALID_PASSWORD + i, MIN_USER_AGE + i);

            registrationService.register(normalUser);
            expectedDbState.add(normalUser);
        }

        for (User user : expectedDbState) {
            assertNotNull(user.getId(), "User must have id after registration");
        }
        assertEquals(Storage.people, expectedDbState, "Unexpected database state");
    }

    @Test
    void register_withId_ok() {
        User user = createUser(-1L, DEFAULT_LOGIN, VALID_PASSWORD, MIN_USER_AGE);
        registrationService.register(user);

        List<User> expectedDbState = new ArrayList<>();
        expectedDbState.add(user);
        assertEquals(Storage.people, expectedDbState, "User must be saved in db");
    }

    @Test
    void register_sameLogin_notOk() {
        User normalUser1 = createUser(DEFAULT_LOGIN, VALID_PASSWORD, MIN_USER_AGE);
        User normalUser2 = createUser(DEFAULT_LOGIN,
                VALID_PASSWORD + VALID_PASSWORD,
                MIN_USER_AGE * 2);
        registrationService.register(normalUser1);

        assertThrows(UserExistException.class,
                () -> registrationService.register(normalUser2));
        List<User> expectedDbState = new ArrayList<>();
        expectedDbState.add(normalUser1);
        assertEquals(Storage.people, expectedDbState, "Only first user must be saved in db");
    }

    @Test
    void register_userTooYoung_notOk() {
        User youngUser = createUser(DEFAULT_LOGIN, VALID_PASSWORD, MIN_USER_AGE - 1);
        assertThrows(UserYoungException.class, () -> registrationService.register(youngUser));
        assertEquals(Storage.people, new ArrayList<>(), "User mustn't be saved");
    }

    @Test
    void register_insecurePassword_notOk() {
        User user = createUser(DEFAULT_LOGIN, "", MIN_USER_AGE);
        assertThrows(PasswordException.class, () -> registrationService.register(user));
        assertEquals(Storage.people, new ArrayList<>(), "User mustn't be saved");
    }

    @Test
    void register_nullFields_notOk() {
        User user = createUser(null, null, null);
        assertThrows(UserNullException.class, () -> registrationService.register(user));
        assertEquals(Storage.people, new ArrayList<>(), "User mustn't be saved");
    }

    @Test
    void register_null_notOk() {
        try {
            registrationService.register(null);
        } catch (NullPointerException e) {
            fail("We should handle situation when user is null");
        } catch (RuntimeException e) {
            assertEquals(e.getClass(), RuntimeException.class);
            assertEquals(Storage.people, new ArrayList<>(), "User mustn't be saved");
            return;
        }
        fail();
    }

    private User createUser(String login, String password, Integer age) {
        return createUser(null, login, password, age);
    }

    private User createUser(Long id, String login, String password, Integer age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
