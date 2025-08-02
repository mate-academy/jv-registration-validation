package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class StorageDaoImlTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_NullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_EmptyUserLogin_NotOk() {
        user.setLogin("");
        user.setPassword("Test@Test");
        user.setAge(30);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LoginLessSixSymbol_NotOk() {
        user.setLogin("Test@");
        user.setPassword("Test@Test");
        user.setAge(25);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_EmptyUserPassword_NotOk() {
        user.setLogin("Test@Test");
        user.setPassword("");
        user.setAge(30);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordLessSixSymbol_NotOk() {
        user.setLogin("Test@Test");
        user.setPassword("Test@");
        user.setAge(25);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAgeIsZero_NotOk() {
        user.setLogin("Test@Test");
        user.setPassword("Test@Test");
        user.setAge(0);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeLessEighteen_NotOk() {
        user.setLogin("Test@Test");
        user.setPassword("Test@Test");
        user.setAge(17);
        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAlreadyExists_NotOk() {

        user.setLogin("Test@Test");
        user.setPassword("Test@Test");
        user.setAge(25);

        User user2 = new User();
        user2.setLogin("Test@Test");
        user2.setPassword("Test@Test");
        user2.setAge(25);

        StorageDaoImpl storageDaoImpl = new StorageDaoImpl();
        storageDaoImpl.add(user2);

        assertThrows(UserNotRegisterException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_addNewUser_Ok() {

        user.setLogin("Test@Test2");
        user.setPassword("Test@Test2");
        user.setAge(33);

        User actual = registrationService.register(user);
        assertNotNull(actual);

    }
}
