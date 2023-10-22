package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setId(0L);
        user.setLogin("login6");
        user.setPassword("qwerty");
        user.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(null));
    }

    @Test
    void register_validUser_Ok() {
        service.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_duplicateUser_notOk() {
        Storage.people.add(user);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin("login");
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setLogin("");
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validLogin_Ok() {
        user.setLogin("login0");
        service.register(user);
        Assertions.assertEquals(user.getLogin(), storageDao.get(user.getLogin()).getLogin());
        user.setLogin("login-login-123//");
        Assertions.assertEquals(user.getLogin(), storageDao.get(user.getLogin()).getLogin());
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword("qwert");
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setPassword("");
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validPassword_Ok() {
        user.setPassword("qwertY");
        service.register(user);
        Assertions.assertEquals(user.getPassword(), storageDao.get(user.getLogin()).getPassword());
        user.setPassword("qwertyQWERTY");
        Assertions.assertEquals(user.getPassword(), storageDao.get(user.getLogin()).getPassword());
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setAge(0);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setAge(-10);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
        user.setAge(null);
        Assertions.assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(18);
        service.register(user);
        Assertions.assertEquals(user.getAge(), storageDao.get(user.getLogin()).getAge());
        user.setAge(30);
        Assertions.assertEquals(user.getAge(), storageDao.get(user.getLogin()).getAge());
    }
}
