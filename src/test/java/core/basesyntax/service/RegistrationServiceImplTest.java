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
    private RegistrationServiceImpl service;
    private StorageDaoImpl storageDao;
    private User userDefault;
    private User userFailLogin;
    private User userFailPassword;
    private User userFailAge;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();

        userDefault = createUserForTest("Denys","12345678",23);
        userFailLogin = createUserForTest("Ma","asdasdd34",21);
        userFailPassword = createUserForTest("Mak","45",21);
        userFailAge = createUserForTest("Mak","asdqwert6",17);
    }

    private User createUserForTest(String login, String pass, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);
        user.setAge(age);
        storageDao.add(user);
        return user;
    }

    @AfterEach
    void afterEachClearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void should_AddUsersInStorage_Default_Ok() {
        int actual = Storage.people.size();
        assertEquals(4, actual);
    }

    @Test
    void should_AddUser_Ok() {
        service.register(userDefault);
        int actual = Storage.people.size();
        assertEquals(5, actual);
    }

    @Test
    void should_ReturnUser_Ok() {
        User expected = storageDao.get(userDefault.getLogin());
        assertEquals(expected, userDefault);
    }

    @Test
    void should_ReturnRunExeptionLogin_NotOk() {
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
