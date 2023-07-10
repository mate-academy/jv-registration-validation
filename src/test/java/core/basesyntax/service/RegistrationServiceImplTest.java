package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;
    private StorageDao storageDao;

    @BeforeEach
    void setup() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
        user = new User();
        user.setLogin("Login123");
        user.setPassword("password123");
        user.setAge(24);
    }

    @Test
    void register_successful_ok() {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_existingUser_throwValidationException() {
        storageDao.add(user);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_throwValidationException() {
        user.setLogin("john");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_throwValidationException() {
        user.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underageUser_throwValidationException() {
        user.setAge(16);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser_throwValidationException() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_throwValidationException() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_throwValidationException() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }
}
