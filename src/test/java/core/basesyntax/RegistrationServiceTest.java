package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {

    private User user;
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void register_nullPassword_notOK() {
        user.setLogin("Login777");
        user.setPassword(null);
        user.setAge(27);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Password should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        user.setPassword("password12");
        user.setAge(22);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Login should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_nullAge_notOK() {
        user.setLogin("Login123123");
        user.setPassword("password12");
        user.setAge(0);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("User age should be at least 18",
                exception.getMessage());
    }

    @Test
    void register_nullUser_notOK() {
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
        Assertions.assertEquals("User is null", exception.getMessage());
    }

    @Test
    void register_existingUser_notOK() {
        user.setLogin("myLogin");
        user.setPassword("myPassword");
        user.setAge(20);
        storageDao.add(user);

        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("User with this login already exist", exception.getMessage());
    }

    @Test
    void register_loginLessThenSix_notOK() {
        user.setLogin("ben");
        user.setPassword("passwordd2");
        user.setAge(30);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Login should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_passwordLessThenSix_NotOk() {
        user.setLogin("Login1123");
        user.setPassword("ok1");
        user.setAge(21);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Password should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_userUnderAge_notOK() {
        user.setLogin("Login1123");
        user.setPassword("123456");
        user.setAge(17);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("User age should be at least 18", exception.getMessage());
    }

    @Test
    void register_user_OK() {
        user.setLogin("Login123123");
        user.setPassword("password159159");
        user.setAge(18);
        Assertions.assertEquals(registrationService.register(user), user);
    }
}
