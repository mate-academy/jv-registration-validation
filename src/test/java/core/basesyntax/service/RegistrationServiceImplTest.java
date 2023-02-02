package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegisterServiceImplExeption;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setPassword("user1Password");
        user1.setAge(25);
        user1.setLogin("user1Login");
        storageDao.add(user1);

        User user2 = new User();
        user2.setPassword("user2Password");
        user2.setAge(35);
        user2.setLogin("user2Login");
        storageDao.add(user2);

        User user3 = new User();
        user3.setPassword("user3Password");
        user3.setAge(35);
        user3.setLogin("user3Login");
        storageDao.add(user3);
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User();
        testUser.setPassword(null);
        testUser.setAge(45);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(45);
        testUser.setLogin(null);
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_notValidAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(17);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_zeroAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(0);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nagativeAge_notOk() {
        User testUser = new User();
        testUser.setPassword("testUserPassword");
        testUser.setAge(-10);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_notValidPassword_notOk() {
        User testUser = new User();
        testUser.setPassword("test");
        testUser.setAge(55);
        testUser.setLogin("testUserLogin");
        assertThrows(RegisterServiceImplExeption.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_addingExistingUser_notOk() {
        User testUser = Storage.people.get(1);
        assertThrows(RegisterServiceImplExeption.class, () -> {
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
        Storage.people.clear();
    }

}