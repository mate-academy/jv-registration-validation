package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    RegistrationServiceImplTest(StorageDaoImpl storageDao) {
        this.storageDao = storageDao;
    }

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    public void register_validUser_ok() {
        User user = new User(2147L, "validLogin", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(user, "User must be registered correctly");
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = new User(2147L, "existingUser", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User(2147L, "short", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User(2147L, "validLogin", "pass", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User user = new User(2147L, "validLogin", "password123", 17);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
