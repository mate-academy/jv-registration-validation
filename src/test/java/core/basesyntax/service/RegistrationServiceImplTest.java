
package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.AuthenticationException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void userContent_ok() {
        User user1 = new User();
        user1.setLogin("User111");
        user1.setPassword("password");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("User222");
        user2.setPassword("password");
        user2.setAge(18);
        User actual = registrationService.register(user1);
        User expectation = user1;
        assertEquals(expectation, actual, "expectation user" + expectation.getLogin()
                + "but received by the user" + actual.getLogin());
        actual = registrationService.register(user2);
        expectation = user2;
        assertEquals(expectation, actual, "expectation " + expectation
                + "but received by the " + actual);
        StorageDaoImpl storage = new StorageDaoImpl();
        actual = storage.get(user1.getLogin());
        Assertions.assertNotNull(actual, "User is not in storage");
        actual = storage.get(user2.getLogin());
        Assertions.assertNotNull(actual, "User is not in storage");
    }

    @Test
    void addExistingUser_notOk() {
        User user1 = new User();
        user1.setLogin("User11");
        user1.setPassword("password");
        user1.setAge(18);
        registrationService.register(user1);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void userLogin_notOk() {
        User user1 = new User();
        user1.setLogin("1234");
        user1.setPassword("password");
        user1.setAge(18);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Login less than 6 characters");
        user1.setLogin("$$$$$");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Login less than 6 characters");
        user1.setLogin("///");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Login less than 6 characters");
        user1.setLogin(".");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(" ");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Login less than 6 characters");
    }

    @Test
    void userExistingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("SuperUser");
        user1.setPassword("password");
        user1.setAge(18);
        registrationService.register(user1);
        User user2 = new User();
        user2.setLogin("SuperUser");
        user2.setPassword("password2");
        user2.setAge(22);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user2);
        }, "A user with this login already exists");
    }

    @Test
    void userPassword_notOk() {
        User user1 = new User();
        user1.setLogin("SuperUser");
        user1.setPassword(" ");
        user1.setAge(18);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password less than 6 characters");
        user1.setPassword("$$$$");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password less than 6 characters");
        user1.setPassword("///");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password less than 6 characters");
        user1.setPassword(".");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password less than 6 characters");
        user1.setPassword(" ");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password less than 6 characters");
    }

    @Test
    void userAge_notOk() {
        User user1 = new User();
        user1.setLogin("SuperUser");
        user1.setPassword("111111");
        user1.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Age less than 18 years");
        user1.setAge(0);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Age less than 18 years");
        user1.setAge(-20);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Age less than 18 years");
    }

    @Test
    void nullLogin_notOk() {
        User user1 = new User();
        user1.setLogin(null);
        user1.setPassword("111111");
        user1.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Login == null");
    }

    @Test
    void nullPassword_notOk() {
        User user1 = new User();
        user1.setLogin("UserSup");
        user1.setPassword(null);
        user1.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Password == null");
    }

    @Test
    void nullAge_notOk() {
        User user1 = new User();
        user1.setLogin("UserSup");
        user1.setPassword("123456");
        user1.setAge(null);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "Age == null");
    }

    @Test
    void nullUser_notOk() {
        User user1 = null;
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user1);
        }, "User == null");
    }
}
