package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.add(new User(1L,"login1", "123456", 50));
        storageDao.add(new User(2L, "login2", "789012", 25));
    }

    @Test
    void register_NotOk_userIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_NotOk_loginFieldIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(new User(null, "", 0)));
    }

    @Test
    void register_NotOk_passwordFieldIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(new User("", null, 0)));
    }

    @Test
    void register_NotOk_ageFieldIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(new User("", "", null)));
    }

    @Test
    void register_NotOk_ageFieldLess18() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(new User("login", "123456", 6)));
    }

    @Test
    void register_NotOk_passwordFieldLengthLess6() {
        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(new User("", "1234", 25)));
    }

    @Test
    void register_NullOk_userInStorage() {
        User user = new User("login1", "123456", 25);
        assertNull(registrationService.register(user));
    }

    @Test
    void register_Ok_registerNewUser() {
        User user = new User("login3", "123456", 25);
        assertNotNull(registrationService.register(user));
        assertEquals(3, Storage.people.size());
        assertEquals(3, Storage.people.get(2).getId());
    }
}
