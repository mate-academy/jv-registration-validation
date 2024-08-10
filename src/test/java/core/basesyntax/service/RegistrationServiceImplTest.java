package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);

        user = new User();
        user.setId(1L);
        user.setLogin("rightLogin");
        user.setPassword("rightPassword");
        user.setAge(25);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);

        User expectedSecondUser = new User();
        expectedSecondUser.setId(2L);
        expectedSecondUser.setLogin("123456");
        expectedSecondUser.setPassword("123456");
        expectedSecondUser.setAge(18);
        User actualSecondUser = registrationService.register(expectedSecondUser);
        assertEquals(expectedSecondUser, actualSecondUser);
    }

    @Test
    void register_userWithExistLogin_notOk() {
        Storage.people.add(user);
        User userWithExistLogin = new User();
        userWithExistLogin.setId(2L);
        userWithExistLogin.setLogin("rightLogin");
        userWithExistLogin.setPassword("password");
        userWithExistLogin.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithExistLogin);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithWrongLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("log");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("login");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithWrongPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("pas");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("passw");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithYoungAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithInvalidAge_notOk() {
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
