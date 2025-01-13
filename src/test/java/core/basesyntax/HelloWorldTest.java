package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserNullException;
import core.basesyntax.service.UserRepeatingException;
import core.basesyntax.service.UserWrongAgeException;
import core.basesyntax.service.UserWrongLoginException;
import core.basesyntax.service.UserWrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private final RegistrationService registrationService
            = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLogin("loginU");
        user.setPassword("123456");
        user.setAge(18);
    }

    @Test
    void userCreated_Ok() {
        Assertions.assertEquals(user,
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        Assertions.assertThrows(UserNullException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_wrongLogin_notOk() {
        user.setLogin("wrong");
        Assertions.assertThrows(UserWrongLoginException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_wrongPassword_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(UserWrongPasswordException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_wrongAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(UserWrongAgeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_repeatUser_notOk() {
        User repeatedUser1 = new User();
        repeatedUser1.setId(2L);
        repeatedUser1.setLogin("repeatedLogin");
        repeatedUser1.setPassword("123456");
        repeatedUser1.setAge(32);
        User repeatedUser2 = new User();
        repeatedUser2.setId(3L);
        repeatedUser2.setLogin("repeatedLogin");
        repeatedUser2.setPassword("654321");
        repeatedUser2.setAge(23);
        registrationService.register(repeatedUser1);
        Assertions.assertThrows(UserRepeatingException.class, () -> {
            registrationService.register(repeatedUser2);
        });
    }
}
