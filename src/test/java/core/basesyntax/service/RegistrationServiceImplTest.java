package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    private User createUser(String login, String password, Integer age) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAge(age);
        return newUser;
    }

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(null);;
        });
    }

    @Test
    void register_nullLoginUser_notOk() {
        User user = createUser(null, "password", 21);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_nullPasswordUser_notOk() {
        User user = createUser("login", null, 21);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_nullAgeUser_notOk() {
        User user = createUser("login", "password", null);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_existingLoginUser_notOk() {
        User user1 = createUser("login", "password1", 18);
        User user2 = createUser("login", "password2", 19);
        User registeredUser1 = registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user2);;
        });
    }

    @Test
    void register_emptyLoginUser_notOk() {
        User user = createUser("", "password1", 21);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_ageLessEighteenUser_notOk() {
        User user = createUser("login1", "password1", 17);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_passwordLessSixCharactersUser_notOk() {
        User user = createUser("login1", "passw", 20);
        assertThrows(RuntimeException.class, () -> {
            User registeredUser = registrationService.register(user);;
        });
    }

    @Test
    void register_addUsers_ok() {
        User user1 = createUser("login1", "password1", 18);
        User user2 = createUser("login2", "password1", 18);
        User user3 = createUser("login3", "password3", 21);
        User registeredUser1 = registrationService.register(user1);
        User registeredUser2 = registrationService.register(user2);
        User registeredUser3 = registrationService.register(user3);
        assertNotEquals(registeredUser1, registeredUser2);
        assertNotEquals(registeredUser1, registeredUser2);
        assertNotEquals(registeredUser2, registeredUser3);
        assertEquals(user1, registeredUser1);
        assertEquals(user2, registeredUser2);
        assertEquals(user3, registeredUser3);
    }
}
