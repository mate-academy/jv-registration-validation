package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("jamesHet", "papahet63", 59);
    }

    @Test
    void register_userAgeNotNull_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_userAgeLessEighteen_NotOK() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_NotOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserExistingLogin_NotOk() {
        User actualUser = new User("jamesHet", "papahet63", 59);
        registrationService.register(actualUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserShortLogin_NotOk() {
        User actualUser = new User("kirk", "63", 59);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_UserNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserShortPassword_NotOk() {
        user.setPassword("asdf");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ValidUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void uninstallations() {
        Storage.people.clear();
    }
}
