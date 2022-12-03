package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
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
public class HelloWorldTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_ok() {
        String login = "Dan";
        user.setLogin(login);
        user.setAge(19);
        user.setPassword("password1");
        User registratedUser = registrationService.register(user);
        assertSame(storageDao.get(login), registratedUser);
    }

    @Test
    void register_existedUser_notOk() {
        user.setLogin("Dan1");
        user.setAge(19);
        user.setPassword("password1");
        storageDao.add(user);
        User user2 = new User();
        user2.setLogin("Dan1");
        user2.setAge(20);
        user2.setPassword("12345678");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        user.setAge(19);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(19);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("Dan");
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLessMinAge_notOk() {
        user.setLogin("Dan");
        user.setAge(17);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageMoreMaxAge_notOk() {
        user.setLogin("Dan");
        user.setAge(121);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNegativeValue_notOk() {
        user.setLogin("Dan");
        user.setAge(-21);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("Dan");
        user.setAge(121);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        user.setLogin("Dan");
        user.setAge(25);
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
