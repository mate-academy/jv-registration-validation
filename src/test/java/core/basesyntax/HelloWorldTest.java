package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private final RegistrationService registrationServiceImpl = new RegistrationServiceImpl();
    private final User defaultUser = new User();

    @BeforeEach
    void setUp() {
        defaultUser.setId(3L);
        defaultUser.setLogin("loginUser");
        defaultUser.setPassword("password");
        defaultUser.setAge(20);
    }

    @Test
    void register_sameLogin_notOk() {
        User secondDefaultUser = defaultUser;
        Storage.people.add(defaultUser);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(secondDefaultUser);
        });
    }

    @Test
    void register_UserLoginHasLessThan6Characters_notOk() {
        defaultUser.setLogin("login");
        assertThrows(RegistrationException.class,() -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_UserLoginEquals0_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RegistrationException.class,() -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_UserPasswordHasLessThan6Characters_notOk() {
        defaultUser.setPassword("pass");
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_UserPasswordEquals0_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_userAgeIsLessThan18_notOk() {
        defaultUser.setAge(16);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_UserAgeEquals0_notOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(defaultUser);
        });
    }

    @Test
    void register_UserIdEquals0_notOk() {
        defaultUser.setId(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(defaultUser);
        });
    }
}

