package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao;
    private User userDefault;
    private User userFailLogin;
    private User userFailPassword;
    private User userFailAge;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();

        userDefault = new User();
        userDefault.setLogin("denys");
        userDefault.setPassword("1245367");
        userDefault.setAge(19);
        storageDao.add(userDefault);

        userFailLogin = new User();
        userFailLogin.setLogin("Ma");
        userFailLogin.setPassword("dqweasd45");
        userFailLogin.setAge(21);
        storageDao.add(userFailLogin);

        userFailPassword = new User();
        userFailPassword.setLogin("Mak");
        userFailPassword.setPassword("45");
        userFailPassword.setAge(21);
        storageDao.add(userFailPassword);

        userFailAge = new User();
        userFailAge.setLogin("Mak");
        userFailAge.setPassword("dqweasd45");
        userFailAge.setAge(17);
        storageDao.add(userFailAge);
    }

    @AfterEach
    void afterEachClearStorage() {
        Storage.people.clear();
    }

    @Test
    void expected_Null() {
        assertThrows(NullPointerException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void shouldAddUsersInStorageDefault() {
        int actual = Storage.people.size();
        assertEquals(4, actual);
    }

    @Test
    void shouldAddUser_Ok() {
        service.register(userDefault);
        int actual = Storage.people.size();
        assertEquals(5, actual);
    }

    @Test
    void shouldReturnUser_Ok() {
        User expected = storageDao.get(userDefault.getLogin());
        assertEquals(expected, userDefault);
    }

    @Test
    void shouldReturnRunExeptionLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailLogin);
        });
    }

    @Test
    void shouldReturnRunExeptionPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailPassword);
        });
    }

    @Test
    void shouldReturnRunExeptionAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailAge);
        });
    }
}
