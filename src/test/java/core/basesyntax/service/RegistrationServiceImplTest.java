package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLogin("rightLogin");
        user.setPassword("rightPassword");
        user.setAge(25);
    }

    @AfterEach
    void tearDown() {
        ((StorageDaoImpl) storageDao).clear();
    }

    @Test
    void register_rightUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);

        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("123456");
        user2.setPassword("123456");
        user2.setAge(18);
        User actual2 = registrationService.register(user2);
        assertEquals(user2, actual2);
    }

    @Test
    void register_userWithExistLogin_notOk() {
        Storage.people.add(user);
        User userWithExistLogin = user;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithExistLogin);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithWrongLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("log");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("login");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithWrongPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("pas");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("passw");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithYoungAge_notOk() {
        user.setAge(15);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithInvalidAge_notOk() {
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
