package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void register_ValidUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(18);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("pass");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underLogin_notOk() {
        User user = new User();
        user.setLogin("oops");
        user.setPassword("password123");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("password123");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(-1);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userDuplicate_notOk() {
        User user = new User();
        user.setLogin("duplicateLogin");
        user.setPassword("password123");
        user.setAge(25);
        registrationService.register(user);

        User user2 = new User();
        user2.setLogin("duplicateLogin");
        user2.setPassword("pass983414123");
        user2.setAge(66);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }
}
