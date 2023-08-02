package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    private static StorageDao storageDao;
    private static User testUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUser = new User();
    }

    @BeforeEach
    void setUp() {
        testUser.setLogin("logins");
        testUser.setPassword("password");
        testUser.setAge(20);
    }

    @Test
    void register_nullAge_NotOk() {
        User actual = new User();
        actual.setAge(null);
        actual.setPassword("password");
        actual.setLogin("logins");
        assertThrows(NullPointerException.class, () ->
                registrationService.register(actual));

    }

    @Test
    void register_notValidAge_NotOk() {
        User actual = new User();
        actual.setAge(8);
        actual.setPassword("password");
        actual.setLogin("logins");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));

    }

    @Test
    void register_validAge_OK() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @Test
    void register_nullLogin_NotOk() {
        User actual = new User();
        actual.setAge(18);
        actual.setLogin(null);
        actual.setPassword("password");
        assertThrows(NullPointerException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_notValidLogin_Not_Ok() {
        User actual = new User();
        actual.setAge(18);
        actual.setLogin("log");
        actual.setPassword("password");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_loginIsStorage_NotOk() {
        storageDao.add(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));

    }

    @Test
    void register_validLogin_OK() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @Test
    void register_nullPassword_NotOk() {
        User actual = new User();
        actual.setAge(18);
        actual.setLogin("logins");
        actual.setPassword(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_notValidPassword_NotOk() {
        User actual = new User();
        actual.setAge(18);
        actual.setLogin("logins");
        actual.setPassword("pas");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_validPassword_Ok() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
