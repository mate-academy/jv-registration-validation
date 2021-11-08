package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    static RegistrationService registrationService;
    static StorageDao storageDao;
    static Exception exception;
    static User user1;
    static User user2;
    static User user3;
    User user;

    @BeforeAll
    static void beforeAll() {
        exception = new RuntimeException();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user1.setLogin("user1");
        user1.setPassword("123456");
        user1.setAge(18);
        user2.setLogin("user2");
        user2.setPassword("123456");
        user2.setAge(18);
        user3.setLogin("user3");
        user3.setPassword("123456");
        user3.setAge(18);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);

        user = new User();
        user.setLogin("okname");
        user.setPassword("okpassword");
        user.setAge(20);
    }

    @Test
    void register_nullLoginUser_NotOk() {
        user.setLogin(null);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_emptyLoginUser_NotOk() {
        user.setLogin("");
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullAgeUser_NotOk() {
        user.setAge(null);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_negativeAgeUser_NotOk() {
        user.setAge(-1);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_lessThan18AgeUser_NotOk() {
        user.setAge(17);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        user.setPassword(null);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_emptyPasswordUser_NotOk() {
        user.setPassword("");
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_lessThan5CharsPasswordUser_NotOk() {
        user.setPassword("12345");
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_alreadyContainsSuchUser_NotOk() {
        storageDao.add(user);
        User expected = null;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(exception.getClass(), () -> {
                    registrationService.register(null);
                });
    }

    @Test
    void register_correctUser_Ok() {
        User expected = user;
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }
}
