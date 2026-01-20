package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registration;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        registration = new RegistrationServiceImpl();
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        user.setLogin("validLoggin");
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_minCharacterPassword_ok() {
        user.setPassword("");
        user.setLogin("validLoggin");
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_password3Char_notOk() {
        user.setPassword("ab2");
        user.setLogin("validLoggin");
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_userValid_ok() {
        user.setPassword("Hello123bc");
        user.setLogin("validLoggin");
        user.setAge(27);

        User result = registration.register(user);

        assertNotNull(result);
        assertEquals("validLoggin", user.getLogin());
    }

    @Test
    void register_nullLoggin_notOk() {
        user.setPassword("abcfhkss2");
        user.setLogin(null);
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_logginSmall_notOk() {
        user.setPassword("msmslsl,sm");
        user.setLogin("abc");
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_logginNothing_notOk() {
        user.setPassword("kmsmsmsms");
        user.setLogin("");
        user.setAge(27);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_ageSmall_notOk() {
        user.setPassword("validPassword");
        user.setLogin("validLoggin");
        user.setAge(17);

        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class,
                () -> registration.register(user));
    }
}
