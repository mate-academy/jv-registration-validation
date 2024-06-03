
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
    private final StorageDaoImpl storage = new StorageDaoImpl();

    @Test
    void userContent_ok() {
        User userFirst = new User();
        userFirst.setLogin("User111");
        userFirst.setPassword("password");
        userFirst.setAge(18);
        User userSecond = new User();
        userSecond.setLogin("User222");
        userSecond.setPassword("password");
        userSecond.setAge(18);
        User actual = registrationService.register(userFirst);
        User expectation = userFirst;
        assertEquals(expectation, actual, "expectation user" + expectation.getLogin()
                + "but received by the user" + actual.getLogin());
        actual = registrationService.register(userSecond);
        expectation = userSecond;
        assertEquals(expectation, actual, "expectation " + expectation
                + "but received by the " + actual);
        actual = storage.get(userFirst.getLogin());
        Assertions.assertNotNull(actual, "User is not in storage");
        actual = storage.get(userSecond.getLogin());
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
        User userTest = new User();
        userTest.setLogin("1234");
        userTest.setPassword("password");
        userTest.setAge(18);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Login less than 6 characters");
        userTest.setLogin("$$$$$");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Login less than 6 characters");
        userTest.setLogin("///");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Login less than 6 characters");
        userTest.setLogin(".");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        });
        userTest.setLogin(" ");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Login less than 6 characters");
    }

    @Test
    void userExistingLogin_notOk() {
        User userTest = new User();
        userTest.setLogin("SuperUser");
        userTest.setPassword("password");
        userTest.setAge(18);
        registrationService.register(userTest);
        userTest.setPassword("password2");
        userTest.setAge(22);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "A user with this login already exists");
    }

    @Test
    void userPassword_notOk() {
        User userTest = new User();
        userTest.setLogin("SuperUser");
        userTest.setPassword(" ");
        userTest.setAge(18);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password less than 6 characters");
        userTest.setPassword("$$$$");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password less than 6 characters");
        userTest.setPassword("///");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password less than 6 characters");
        userTest.setPassword(".");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password less than 6 characters");
        userTest.setPassword(" ");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password less than 6 characters");
    }

    @Test
    void userAge_notOk() {
        User userTest = new User();
        userTest.setLogin("SuperUser");
        userTest.setPassword("111111");
        userTest.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Age less than 18 years");
        userTest.setAge(0);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Age less than 18 years");
        userTest.setAge(-20);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Age less than 18 years");
    }

    @Test
    void nullLogin_notOk() {
        User userTest = new User();
        userTest.setLogin(null);
        userTest.setPassword("111111");
        userTest.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Login == null");
    }

    @Test
    void nullPassword_notOk() {
        User userTest = new User();
        userTest.setLogin("UserSup");
        userTest.setPassword(null);
        userTest.setAge(17);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Password == null");
    }

    @Test
    void nullAge_notOk() {
        User userTest = new User();
        userTest.setLogin("UserSup");
        userTest.setPassword("123456");
        userTest.setAge(null);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "Age == null");
    }

    @Test
    void nullUser_notOk() {
        User userTest = null;
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(userTest);
        }, "User == null");
    }
}
