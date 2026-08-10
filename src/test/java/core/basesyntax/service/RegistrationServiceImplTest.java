package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.UserValidationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_ageExactly18_ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-5);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordExactly6_ok() {
        user.setPassword("123456");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_passwordLength5_notOk() {
        user.setPassword("aaaaa"); // межа - на 1 менше за поріг
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordLength0_notOk() {
        user.setPassword(""); // межа - на 1 менше за поріг
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLength5_notOk() {
        user.setLogin("aaaaa"); // межа - на 1 менше за поріг
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginExactly6_ok() {
        user.setLogin("qwerty");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(user);
        User secondUser = new User();
        secondUser.setLogin("validLogin");
        secondUser.setPassword("anotherPassword");
        secondUser.setAge(25);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("abc");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("adse");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underageUser_notOk() {
        user.setAge(17);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
