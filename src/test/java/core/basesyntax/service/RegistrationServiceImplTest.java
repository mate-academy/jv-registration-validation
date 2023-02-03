package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private static User testUser = new User();
    private static final int MIN_AGE = 18;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser.setAge(MIN_AGE);
        testUser.setLogin("login");
        testUser.setPassword("123456");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_LoginNull_NotOK() {
        testUser.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_PasswordNull_NotOK() {
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_AgeNull_NotOK() {
        testUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_LoginNotUnique_NotOk() {
        registrationService.register(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_PasswordLessThanRequiredMinimumLength_NotOk() {
        testUser.setPassword("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_AgeLessThanRequiredMinimum_NotOk() {
        testUser.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_WithValidParameters_OK() {
        registrationService.register(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()));
    }
}
