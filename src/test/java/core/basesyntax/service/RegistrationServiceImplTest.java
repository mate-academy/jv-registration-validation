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

        userDefault = createUser("Denys","12345678",23);
        userFailLogin = createUser("Ma","asdasdd34",21);
        userFailPassword = createUser("Mak","45",21);
        userFailAge = createUser("Mak","asdqwert6",17);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_AddUser_Ok() {
        service.register(userDefault);
        int actual = Storage.people.size();
        assertEquals(5, actual);
    }

    @Test
    void register_ChecksLogin_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailLogin);
        });
    }

    @Test
    void register_ChecksPassword_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailPassword);
        });
    }

    @Test
    void register_ChecksAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(userFailAge);
        });
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        storageDao.add(user);
        return user;
    }
}
