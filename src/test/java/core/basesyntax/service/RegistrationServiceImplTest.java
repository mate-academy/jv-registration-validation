package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        User actual = testUser;
        actual.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_notValidAge_notOk() {
        User actual = testUser;
        actual.setAge(8);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_validAge_Ok() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @Test
    void register_nullLogin_notOk() {
        User actual = testUser;
        actual.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_notValidLogin_notOk() {
        User actual = testUser;
        actual.setLogin("log");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_loginIsStorage_notOk() {
        storageDao.add(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_validLogin_Ok() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @Test
    void register_nullPassword_notOk() {
        User actual = testUser;
        actual.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_notValidPassword_notOk() {
        User actual = testUser;
        actual.setPassword("pas");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_validPassword_Ok() {
        User actual = testUser;
        assertDoesNotThrow(() -> registrationService.register(actual));
    }

    @Test
    void userIsStorageAfterRegistration_Ok() {
        registrationService.register(testUser);
        String actual = storageDao.get(testUser.getLogin()).getLogin();
        String expected = testUser.getLogin();
        assertEquals(expected,actual);
    }
}
