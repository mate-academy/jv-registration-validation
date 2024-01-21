package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeption.FailedToAddUser;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private final RegistrationService registrationServiceImpl = new RegistrationServiceImpl();

    @Test
    void register_sameLogin_notOk() {
        User user1 = new User(1L, "loginUser", "password", 18);
        User user2 = new User(2L, "loginUser", "password", 20);
        assertDoesNotThrow(() -> registrationServiceImpl.register(user1));
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user2);
        });
    }

    @Test
    void register_UserLoginHasLessThan6Characters_notOk() {
        User user = new User(3L, "login", "password", 20);
        assertThrows(FailedToAddUser.class,() -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_UserLoginEquals0_notOk() {
        User user = new User(3L, null, "password", 20);
        assertThrows(FailedToAddUser.class,() -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_UserPasswordHasLessThan6Characters_notOk() {
        User user = new User(3L, "loginUser", "pass", 20);
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_UserPasswordEquals0_notOk() {
        User user = new User(3L, "loginUser", null, 20);
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userAgeIsLessThan18_notOk() {
        User user = new User(3L, "loginUser", "password", 16);
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_UserAgeEquals0_notOk() {
        User user = new User(3L, "loginUser", "password", null);
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_UserIdEquals0_notOk() {
        User user = new User(null, "loginUser", "password", 20);
        assertThrows(FailedToAddUser.class, () -> {
            registrationServiceImpl.register(user);
        });
    }
}

