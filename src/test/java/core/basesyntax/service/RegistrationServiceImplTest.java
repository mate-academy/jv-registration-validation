package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_PASSWORD_AND_LOGIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegisterException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void successfullyUserRegister_Ok() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("password");
        user.setAge(20);

        User register = registrationService.register(user);
        assertEquals(user, register);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void userAlreadyExists_NotOk() {
        User user = new User();
        user.setLogin("existing login");
        user.setAge(20);
        user.setPassword("password");
        Storage.people.add(user);

        User newUser = new User();
        newUser.setLogin("existing login");
        newUser.setPassword("new user password");
        newUser.setAge(19);

        assertThrows(RegisterException.class,
                () -> registrationService.register(newUser),
                "Login already exists! Enter another value");
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RegisterException.class,
                () -> registrationService.register(null),
                "User can not be null");
    }

    @Test
    void userLoginIsNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setAge(19);
        user.setPassword("password");

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Login can not be null!");
    }

    @Test
    void userLoginLength_NotOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("password");
        user.setAge(19);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Minimum login length is " + MIN_PASSWORD_AND_LOGIN_LENGTH + ". Try again");
    }

    @Test
    void userPasswordIsNull_NotOk() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Password can not be null");
    }

    @Test
    void userPasswordLength_NotOk() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                ("Minimum password length is " + MIN_PASSWORD_AND_LOGIN_LENGTH + ". Try again"));
    }

    @Test
    void userAgeIsNull_NotOk() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("password");
        user.setAge(null);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Age can not be null");
    }

    @Test
    void usesAge_NotOk() {
        User user = new User();
        user.setLogin("test login");
        user.setPassword("password");
        user.setAge(17);

        assertThrows(RegisterException.class,
                () -> registrationService.register(user),
                "Minimum age is 18. Your age: " + user.getAge());
    }
}
