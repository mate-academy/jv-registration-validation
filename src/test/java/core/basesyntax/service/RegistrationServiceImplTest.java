package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static User newUser;
    private static User userWithExistedLogin;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setAge(28);
        newUser.setLogin("BorysenkoDanyl");
        newUser.setPassword("Danyl5555");
        userWithExistedLogin = new User();
        userWithExistedLogin.setAge(25);
        userWithExistedLogin.setLogin("BorysenkoDanyl");
        userWithExistedLogin.setPassword("Borysenko");
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
        newUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        newUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        newUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_newUser_Ok() {
        registrationService.register(newUser);
        assertTrue(Storage.people.contains(newUser));
    }

    @Test
    void register_existedUser_NotOk() {
        storageDao.add(newUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithExistedLogin);
        });
    }

    @Test
    void register_invalidAge_NotOk() {
        newUser.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_minAge_Ok() {
        newUser.setAge(18);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void register_validAge_Ok() {
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void register_invalidPassword_NotOk() {
        newUser.setPassword("user");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_minLengthPassword_Ok() {
        newUser.setPassword("Danyl@");
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void register_validPassword_Ok() {
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }
}
