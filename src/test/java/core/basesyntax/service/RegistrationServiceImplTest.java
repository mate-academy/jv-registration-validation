package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user1;
    private User user2;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setLogin("first");
        user1.setAge(21);
        user1.setPassword("registration");
    }

    @Test
    void register_uniqueLogin_Ok() {
        User registeredUser = registrationService.register(user1);
        assertEquals(user1, registeredUser);
        assertTrue(Storage.people.contains(user1));
        assertEquals("first", storageDao.get("first").getLogin());
        assertEquals(21, storageDao.get("first").getAge());
        assertEquals("registration", storageDao.get("first").getPassword());
    }

    @Test
    void register_uniqueLogin_NotOk() {
        storageDao.add(user1);
        user2 = new User();
        user2.setLogin("first");
        user2.setAge(21);
        user2.setPassword("registration");
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_NullLogin_NotOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        user1.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_Age_Ok() {
        registrationService.register(user1);
        assertEquals("first", storageDao.get("first").getLogin());
        assertEquals(21, storageDao.get("first").getAge());
        assertEquals("registration", storageDao.get("first").getPassword());
    }

    @Test
    void register_18Age_Ok() {
        user1.setAge(18);
        registrationService.register(user1);
        assertEquals("first", storageDao.get("first").getLogin());
        assertEquals(18, storageDao.get("first").getAge());
        assertEquals("registration", storageDao.get("first").getPassword());
    }

    @Test
    void register_NullAge_NotOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_LowAge_NotOk() {
        user1.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_0Age_NotOk() {
        user1.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_NegativeAge_NotOk() {
        user1.setAge(-5869);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_Password_Ok() {
        registrationService.register(user1);
        assertEquals("first", storageDao.get("first").getLogin());
        assertEquals(21, storageDao.get("first").getAge());
        assertEquals("registration", storageDao.get("first").getPassword());
    }

    @Test
    void register_PoorPassword_NotOk() {
        user1.setPassword("liluu");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_NullPassword_NotOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_EmptyPassword_NotOk() {
        user1.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
