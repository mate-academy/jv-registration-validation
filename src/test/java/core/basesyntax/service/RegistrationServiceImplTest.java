package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeAll
        public void setUp() {
            registrationService = new RegistrationServiceImpl();
    }

    RegistrationServiceImplTest(StorageDaoImpl storageMock) {
        this.storageDao = storageMock;
    }

    @Test
    public void register_validUser_ok() {
        User user = new User("validLogin", "password123", 20);

        User registeredUser = registrationService.register(user);

        assertEquals(user, registeredUser);

        User actualUserFromStorage = storageDao.get(user.getLogin());
        assertNotNull(actualUserFromStorage);
        assertEquals(user, actualUserFromStorage);
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = new User("existingUser", "password123", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User("short", "password123", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User("validLogin", "pass", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User user = new User("validLogin", "password123", 17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
