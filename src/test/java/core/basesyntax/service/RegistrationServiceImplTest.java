package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationDataInvalidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("1234556");
        user.setAge(20);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("test123@gmail.com");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("test123@gmail.com");
        user.setPassword("1234567");
        user.setAge(null);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("123");
        user.setPassword("1234567");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("1234567");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthFive_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("1234567");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("1234");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthFive_notOk() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("12345");
        user.setAge(24);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("1234567");
        user.setAge(17);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age18_ok() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("1234566");
        user.setAge(18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
        User fromStorage = storageDao.get(user.getLogin());
        assertNotNull(fromStorage);
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("1234566");
        user.setAge(-1);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        User user2 = new User();
        user1.setLogin("test123@gmai.com");
        user2.setLogin("test123@gmai.com");
        user1.setPassword("1234567");
        user2.setPassword("12345675");
        user1.setAge(20);
        user2.setAge(19);
        storageDao.add(user1);
        assertThrows(RegistrationDataInvalidException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("test123@gmai.com");
        user.setPassword("1234566");
        user.setAge(24);
        registrationService.register(user);
        User result = storageDao.get(user.getLogin());
        assertNotNull(result);
    }
}
