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
        testUser.setLogin("logins78");
        testUser.setPassword("password");
        testUser.setAge(38);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validDataUser_ok() {
        assertDoesNotThrow(() -> registrationService.register(testUser));
        String actual = storageDao.get(testUser.getLogin()).getLogin();
        String expected = testUser.getLogin();
        assertEquals(expected,actual);
    }

    @Test
    void register_nullAge_notOk() {
        User actual = testUser;
        actual.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_lessMinAge_notOk() {
        User actual = testUser;
        actual.setAge(8);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
        actual.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void registration_negativeAge_notOk() {
        User actual = testUser;
        actual.setAge(-18);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_nullLogin_notOk() {
        User actual = testUser;
        actual.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_shortLogin_notOk() {
        User actual = testUser;
        actual.setLogin("log");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
        actual.setLogin("login");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_loginIsTaken_notOk() {
        storageDao.add(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }
    
    @Test
    void register_nullPassword_notOk() {
        User actual = testUser;
        actual.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }

    @Test
    void register_shortPassword_notOk() {
        User actual = testUser;
        actual.setPassword("pas");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
        actual.setPassword("passw");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(actual));
    }
}
