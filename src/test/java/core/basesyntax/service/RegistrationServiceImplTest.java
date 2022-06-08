package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    public static StorageDaoImpl storage;
    private User userBob;
    private User user;
    private RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        storage.add(new User("BobAlreadyExists", "password", 19));
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User("Alice", "Johnson", 63);
        userBob = new User("Bob", "SomeLength", 22);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void check_if_login_exists_notOk() {
        user.setLogin(userBob.getLogin());
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void login_is_null_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_is_null_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_is_null_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void check_if_login_exists_is_ok() {
        user.setLogin("NotBob");
        User expected = user;
        User actual = registrationService.register(userBob);
        assertNotEquals(expected, actual);
    }

    @Test
    void password_is_ok() {
        user.setPassword("ThisPasswordIsLongEnough");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void login_is_ok() {
        user.setLogin("SomeSmartName");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void age_is_ok() {
        user.setAge(19);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }
}