package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Valid_Login");
        user.setPassword("Valid_Password");
        user.setAge(23);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_manyInvalidAge_notOk() {
            user.setAge(10);
            assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        user.setPassword("Abba");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addCreatingUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneNewUser_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        User expected = registrationService.register(user);
        User current = storageDao.get(user.getLogin());

        assertEquals(expected, current);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}