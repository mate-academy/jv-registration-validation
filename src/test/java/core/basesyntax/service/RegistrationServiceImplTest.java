package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void registerAge_lessThan18_NotOk() {
        user.setAge(5);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerAge_isNegative_NotOk() {
        user.setAge(-19);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerAge_isNull_NotOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerAge_over18_Ok() {
        final StorageDaoImpl storageDao = new StorageDaoImpl();
        user.setLogin("Bob");
        user.setAge(32);
        user.setPassword("randomPassword");
        registrationService.register(user);
        assertEquals(32, storageDao.get(user.getLogin()).getAge());
    }

    @Test
    void registerUser_isNew_Ok() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void registerUser_isNull_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_isEmpty_NotOk() {
        String emptyLogin = "";
        user.setLogin(emptyLogin);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_notNew_NotOk() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        user.setLogin("Bob");
        User newUser = new User();
        newUser.setLogin("Bob");
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerPassword_isLongerThan5_Ok() {
        final StorageDaoImpl storageDao = new StorageDaoImpl();
        String goodPassword = "qwerty";
        user.setPassword(goodPassword);
        user.setLogin("Bob");
        user.setAge(32);
        registrationService.register(user);
        assertEquals(goodPassword, storageDao.get(user.getLogin()).getPassword());
    }

    @Test
    void registerPassword_isShorterThan6_NotOk() {
        String shortPassword = "qwert";
        user.setPassword(shortPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPassword_isNull_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPassword_isEmpty_NotOk() {
        String emptyPassword = "";
        user.setPassword(emptyPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
