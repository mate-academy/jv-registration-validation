package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("DreamFERAL");
        testUser.setPassword("Qwerty7940813");
        testUser.setAge(34);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyExist_notOk() {
        storageDao.add(testUser);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_userIsNull_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_userDoesNotExist_Ok() {
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_passwordIsNull_notOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        testUser.setPassword("");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_passwordIsShort_notOK() {
        testUser.setPassword("777");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }

    @Test
    void register_ageIsLow_notOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(testUser));
    }
}
