import org.junit.jupiter.api.*;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationService {

    private RegistrationService service;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear(); // 🔥 обязательно из-за static storage
        storageDao = new StorageDaoImpl();
        service = new RegistrationService(storageDao);
    }

    @Test
    void register_validUser_ok() {
        User user = new User("validUser", "password", 20);

        service.register(user);

        assertNotNull(storageDao.get("validUser"));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User("user1", "password", 20);
        storageDao.add(user);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("usr", "password", 20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("validUser", "123", 20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User("validUser", "password", 17);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_ageBoundary18_ok() {
        User user = new User("validUser", "password", 18);

        service.register(user);

        assertNotNull(storageDao.get("validUser"));
    }

    @Test
    void register_loginBoundary6_ok() {
        User user = new User("123456", "password", 20);

        service.register(user);

        assertNotNull(storageDao.get("123456"));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }
}
