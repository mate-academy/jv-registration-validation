package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User testUser;
    private static RegistrationService testRegistration;

    @BeforeAll
    static void atStartOnce() {
        StorageDao testStorage = new StorageDaoImpl();
        testRegistration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(23);
        testUser.setLogin("SoulARC");
        testUser.setPassword("qwerty");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_checkingForNonExistentUser_Ok() {
        assertEquals(testUser, testRegistration.register(testUser));
    }

    @Test
    void register_gotNullUser_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotExistingUsername_notOk() {
        testRegistration.register(testUser);
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotNullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotEmptyLogin_notOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotShortPassword_notOk() {
        testUser.setPassword("55555");
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotEmptyPassword_notOk() {
        testUser.setPassword("");
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotNullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }

    @Test
    void register_gotLowAge_notOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, ()
                -> testRegistration.register(testUser));
    }
}


