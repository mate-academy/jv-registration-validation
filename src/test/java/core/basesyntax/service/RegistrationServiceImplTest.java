package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static User user = new User();
    private static RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        user.setLogin("surnuk");
        user.setPassword("admin123");
        user.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userPresent_notOk() {
        User sameLogin = user;
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(sameLogin));
    }

    @Test
    void register_userValid_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_userNull_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
