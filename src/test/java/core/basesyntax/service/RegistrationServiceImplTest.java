package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static Storage storage;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        storage = new Storage();
    }

    @BeforeEach
    void setUp() {
        User firstUser = new User();
        firstUser.setPassword("firstUserPassword");
        firstUser.setAge(25);
        firstUser.setLogin("firstUserLogin");
        storageDao.add(firstUser);

        User secondUser = new User();
        secondUser.setPassword("secondUserPassword");
        secondUser.setAge(35);
        secondUser.setLogin("secondUserLogin");
        storageDao.add(secondUser);

        User thirddUser = new User();
        thirddUser.setPassword("thirddUserPassword");
        thirddUser.setAge(35);
        thirddUser.setLogin("thirddUserLogin");
        storageDao.add(thirddUser);
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User();
        testUser.setPassword(null);
        testUser.setAge(45);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(45);
        testUser.setLogin(null);
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_notValidAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(17);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_zeroAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(0);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nagativeAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(-10);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_notValidPassword_notOk() {
        User testUser = new User();
        testUser.setPassword("test");
        testUser.setAge(55);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_addingExistingUser_notOk() {
        User testUser = storage.people.get(1);
        assertThrows(RegisterServiceImplException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_addingValidUser_Ok() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(30);
        testUser.setLogin("testUserLogin");
        User actual = registrationService.register(testUser);
        assertEquals("testUserPassword", actual.getPassword());
        assertEquals(30, actual.getAge());
        assertEquals("testUserLogin", actual.getLogin());
    }

    @AfterEach
    void tearDown() {
        storage.people.clear();
    }
}
