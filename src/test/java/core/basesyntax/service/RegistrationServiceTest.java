package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
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
    void register_userNull_notOK() {
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
        Assertions.assertEquals("User is null", exception.getMessage());
    }

    @Test
    void register_existingUser_notOK() {
        user.setLogin("myLogin");
        user.setPassword("somePassword");
        user.setAge(20);
        storageDao.add(user);

        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("User with this login already exist", exception.getMessage());
    }

    @Test
    void register_loginLessSix_notOK() {
        user.setLogin("123");
        user.setPassword("normal1");
        user.setAge(30);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Login should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_loginNull_notOK() {
        user.setLogin(null);
        user.setPassword("normal1");
        user.setAge(30);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Login should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_passwordLessSix_NotOk() {
        user.setLogin("Login1123");
        user.setPassword("1");
        user.setAge(21);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("Password should contain at least 6 characters",
                exception.getMessage());
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setLogin("Login1123");
        user.setPassword(null);
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
    void register_userAgeNull_notOK() {
        user.setLogin("Login1123");
        user.setPassword("123456");
        user.setAge(null);
        Exception exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        Assertions.assertEquals("User age should be at least 18", exception.getMessage());
    }

    @Test
    void register_user_OK() {
        user.setLogin("Correct");
        user.setPassword("correct123");
        user.setAge(18);
        Assertions.assertEquals(registrationService.register(user), user);
    }
}
