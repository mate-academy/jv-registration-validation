package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        user = new User();
    }

    @Test
    public void register_validUser_ok() {
        user.setLogin("validlogin1234");
        user.setPassword("validpassword1234");
        user.setAge(20);

        User savedUser = registrationService.register(user);

        assertNotNull(savedUser);
        assertEquals(user.getLogin(), savedUser.getLogin());
        assertNotNull(storageDao.get("validlogin1234"));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        user.setLogin("duplicateLogin1234");
        user.setPassword("pass1234");
        user.setAge(20);
        Storage.people.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("1234");
        user.setPassword("pass1234");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setLogin("123456789");
        user.setPassword("1234");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_underage_notOk() {
        user.setLogin("123456789");
        user.setPassword("123456789");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginLength6_ok() {
        user.setLogin("123456");
        user.setPassword("pass1234");
        user.setAge(20);

        User savedUser = registrationService.register(user);

        assertNotNull(savedUser);
    }

    @Test
    public void register_loginPassword6_ok() {
        user.setLogin("123456789");
        user.setPassword("123456");
        user.setAge(20);

        User savedUser = registrationService.register(user);

        assertNotNull(savedUser);
    }

    @Test
    public void register_age18_ok() {
        user.setLogin("123456789");
        user.setPassword("123456789");
        user.setAge(18);

        User savedUser = registrationService.register(user);

        assertNotNull(savedUser);
    }

    @Test
    public void register_ageBellow0_notOk() {
        user.setLogin("123456789");
        user.setPassword("123456789");
        user.setAge(-18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginNull_notOk() {
        user.setLogin(null);
        user.setPassword("123456789");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordNull_notOk() {
        user.setLogin("123456789");
        user.setPassword(null);
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageNull_notOk() {
        user.setLogin("123456789");
        user.setPassword("123456789");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_emptyPassword_notOk() {
        user.setLogin("123456789");
        user.setPassword("");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordLength3_notOk() {
        user.setLogin("123456789");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordLength5_notOk() {
        user.setLogin("123456789");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginLength5_notOk() {
        user.setLogin("12345");
        user.setPassword("123456");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
