package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser = new User(1L, "validLogin", "validPassword", 21);
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            User invalidUser = new User(2L, null, "validPassword", 21);
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            User invalidUser = new User(2L, "short", "validPassword", 21);
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            User invalidUser = new User(2L, "validLogin", "short", 21);
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginAlreadyPresent_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            User invalidUser = new User(2L, "validLogin", null, 21);
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_lowAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            validUser.setAge(4);
            registrationService.register(validUser);
        });
    }

    @Test
    void register_edgeAge_Ok() {
        User user = new User(3L, "validLog", "validPassword", 18);
        registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, user);
    }

    @Test
    void register_edgeLogin_Ok() {
        validUser.setLogin("123456");
        registrationService.register(validUser);
        User expected = storageDao.get(validUser.getLogin());
        assertEquals(expected, validUser);
    }

    @Test
    void register_edgePassword_Ok() {
        validUser.setPassword("123456");
        registrationService.register(validUser);
        User expected = storageDao.get(validUser.getLogin());
        assertEquals(expected, validUser);
    }

    @Test
    void register_validUser_Ok() {
        //how to use one instance with before each?
        User expected = new User(4L, "validLog2", "validPassword", 21);
        registrationService.register(expected);
        User actual = storageDao.get(expected.getLogin());
        assertEquals(expected, actual);
    }
}
