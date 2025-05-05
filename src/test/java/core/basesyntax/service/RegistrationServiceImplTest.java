package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_Ok() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(22);
        user.setPassword("123456");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_minAge_Ok() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(18);
        user.setPassword("123456");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setAge(22);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(22);
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinAge_NotOk() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(17);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        User user = new User();
        user.setLogin("");
        user.setAge(20);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThanMin_NotOk() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(20);
        user.setPassword("12345");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_minPasswordLength_Ok() {
        User user = new User();
        user.setLogin("User_1");
        user.setAge(20);
        user.setPassword("123456");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_loginWithWhiteSpace_NotOk() {
        User user = new User();
        user.setLogin("User 1");
        user.setAge(20);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userWithExistingLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("User_1");
        user1.setAge(20);
        user1.setPassword("123456");
        User user2 = new User();
        user2.setLogin("User_1");
        user2.setAge(21);
        user2.setPassword("123456");
        registrationService.register(user1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_twoValidUsers_Ok() {
        User user1 = new User();
        user1.setLogin("User_1");
        user1.setAge(20);
        user1.setPassword("123456");
        User user2 = new User();
        user2.setLogin("User_2");
        user2.setAge(20);
        user2.setPassword("123456");
        User actual1 = registrationService.register(user1);
        User actual2 = registrationService.register(user2);
        User expected1 = storageDao.get(user1.getLogin());
        User expected2 = storageDao.get(user2.getLogin());
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }
}
