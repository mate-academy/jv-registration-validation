
package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_Password_IsOk() {
        String password = "hfjkghyr";
        boolean actual = registrationService.passwordIsValid(password);
        assertTrue(actual);
    }

    @Test
    void register_Password_NotOk() {
        String password = "hfjk";
        boolean actual = registrationService.passwordIsValid(password);
        assertFalse(actual);
    }

    @Test
    void register_NullPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.passwordIsValid(null);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.ageIsValid(null);
        });
    }

    @Test
    void register_Age_IsOk() {
        int age = 21;
        boolean actual = registrationService.ageIsValid(age);
        assertTrue(actual);
    }

    @Test
    void register_Age_NotOk() {
        int age = 16;
        boolean actual = registrationService.ageIsValid(age);
        assertFalse(actual);
    }

    @Test
    void register_NoLogin_IsOk() {
        String login = "Yevheniy";
        User actual = storageDao.get(login);
        assertNull(actual);
    }

    @Test
    void register_NullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            storageDao.get(null);
        });
    }
}
