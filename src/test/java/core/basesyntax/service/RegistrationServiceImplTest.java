package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CreateUserException;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void allDataIsValid_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals("validLogin", registeredUser.getLogin());
    }

    @Test
    void passwordIsNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        user.setPassword(null);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_notOk() {
        User user = new User();
        user.setPassword("12345678");
        user.setAge(20);
        user.setLogin(null);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345678");
        user.setAge(null);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void invalidLogin_notOk() {
        User user = new User();
        user.setLogin("val");
        user.setPassword("12345678");
        user.setAge(19);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void invalidAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345678");
        user.setAge(3);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void invalidPassword_notOK() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(19);

        assertThrows(WrongDataException.class, () -> {
            registrationService.register(user);
        });
    }
    /*
 need help with it
    @Test
    void dublicateOfUser_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345678");
        user.setAge(19);

        User dublicateUser = new User();
        dublicateUser.setLogin("validLogin");
        dublicateUser.setPassword("12345678");
        dublicateUser.setAge(19);

        registrationService.register(user);
        assertThrows(WrongDataException.class, () -> {
            registrationService.register(dublicateUser);
        });
    }*/
    @Test
    void userIsNull_notOk() {
        User user = null;

        assertThrows(CreateUserException.class, () -> {
            registrationService.register(user);
        });
    }
}
