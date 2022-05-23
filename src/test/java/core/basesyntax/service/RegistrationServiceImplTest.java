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
    private static StorageDao testStorage;
    private static User testUser;
    private static RegistrationService testRegistration;

    @BeforeAll
    static void atStartOnce() {
        testStorage = new StorageDaoImpl();
        testRegistration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(100);
        testUser.setLogin("abcdef");
        testUser.setPassword("1234567");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_gotValidNotExistingUser_Ok() {
        assertEquals(testUser, testRegistration.register(testUser));
    }

    @Test
    void register_gotExistingUsername_notOk() {
        testRegistration.register(testUser);
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotNullUser_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotNullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotEmptyLogin_notOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotSmallAge_notOk() {
        testUser.setAge(-100);
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotNullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotEmptyPassword_notOk() {
        testUser.setPassword("");
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }

    @Test
    void register_gotTooShortPassword_notOk() {
        testUser.setPassword("1234");
        assertThrows(RuntimeException.class, () -> testRegistration.register(testUser));
    }
}
