package core.basesyntax;

import core.basesyntax.exception.RegistrationDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static User user;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void createValidUser() {
        user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(33);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("short");
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("short");
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(user);
        User anotherUser = new User();
        anotherUser.setLogin("validLogin");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setAge(22);
        Assertions.assertThrows(RegistrationDataException.class, () -> {
            registrationService.register(anotherUser);
        });
    }

    @Test
    void register_validUser_ok() {
        User newUser = new User();
        newUser.setLogin("newLogin");
        newUser.setPassword("newPassword");
        newUser.setAge(25);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(newUser);
        });
    }
}
