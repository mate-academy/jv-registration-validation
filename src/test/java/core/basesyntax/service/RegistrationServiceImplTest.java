package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setAge(28);
        user1.setLogin("BorysenkoDanyl");
        user1.setPassword("Danyl5555");
        user2 = new User();
        user2.setAge(25);
        user2.setLogin("BorysenkoDanyl");
        user2.setPassword("Borysenko");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_newUser_Ok() {
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user1));
    }

    @Test
    void register_existedUser_NotOk() {
        storageDao.add(user1);
        try {
            registrationService.register(user2);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if user with such login is already exists");
    }

    @Test
    void register_invalidAge_NotOk() {
        user1.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_minAge_Ok() {
        user1.setAge(18);
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
    }

    @Test
    void register_validAge_Ok() {
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
    }

    @Test
    void register_invalidPassword_NotOk() {
        user1.setPassword("user");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_minLengthPassword_Ok() {
        user1.setPassword("Danyl@");
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
    }

    @Test
    void register_validPassword_Ok() {
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
    }
}
