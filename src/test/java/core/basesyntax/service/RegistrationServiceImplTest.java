package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    private User createUser(String login, String password, Integer age) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAge(age);
        return newUser;
    }

    @Test
    void register_nullUser_notOk() {
        try {
            User registeredUser = registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException have to thrown if user is null ");
    }

    @Test
    void register_nullLoginUser_notOk() {
        User user = createUser(null, "password", 21);
        try {
            User registeredUser = registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException have to thrown if user is null ");
    }

    @Test
    void register_nullPasswordUser_notOk() {
        User user = createUser("login", null, 21);
        try {
            User registeredUser = registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException have to thrown if user is null ");
    }

    @Test
    void register_nullAgeUser_notOk() {
        User user = createUser("login", "password", null);
        try {
            User registeredUser = registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException have to thrown if user is null ");
    }

    @Test
    void register_addUserWithExistingLogin_notOk() {
        User user1 = createUser("login", "password1", 18);
        User user2 = createUser("login", "password2", 19);
        User registeredUser1 = registrationService.register(user1);
        User registeredUser2 = registrationService.register(user2);
        assertNotNull(registeredUser1);
        assertNull(registeredUser2);
    }

    @Test
    void register_addUserWithAgeLessEighteen_notOk() {
        User user = createUser("login1", "password1", 17);
        User registeredUser = registrationService.register(user);
        assertNull(registeredUser);
    }

    @Test
    void register_addUserWithPasswordLessSix_notOk() {
        User user = createUser("login1", "passw", 20);
        User registeredUser = registrationService.register(user);
        assertNull(registeredUser);
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
