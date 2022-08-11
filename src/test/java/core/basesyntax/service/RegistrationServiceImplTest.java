package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MAX_AGE;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static User testUser;
    private static User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUser = new User();
        actual = new User();
    }

    @BeforeEach
    void setUp() {
        testUser.setLogin("Elvis");
        testUser.setAge(MIN_AGE);
        testUser.setPassword("_elviS");
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_alreadyExistsUser_NotOK() {
        storageDao.add(testUser);
        testUser.setLogin("Elvis");
        testUser.setAge(MAX_AGE);
        testUser.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_validMaxAgeUser_Ok() {
        testUser.setLogin("ElvisMaxAge");
        testUser.setAge(MAX_AGE);
        testUser.setPassword("123456");
        actual = registrationService.register(testUser);
        assertTrue(MAX_AGE > MIN_AGE);
        assertEquals(testUser, actual);
    }

    @Test
    void register_validMinAgeUser_Ok() {
        testUser.setLogin("ElvisMinAge");
        testUser.setAge(MIN_AGE);
        testUser.setPassword("123456");
        actual = registrationService.register(testUser);
        assertTrue(MAX_AGE > MIN_AGE);
        assertEquals(testUser, actual);
    }

    @Test
    void register_ageMoreMin_Ok() {
        testUser.setLogin("Elvis2");
        testUser.setAge(MIN_AGE + 1);
        testUser.setPassword("123456");
        actual = registrationService.register(testUser);
        assertTrue(MAX_AGE > MIN_AGE);
        assertEquals(testUser, actual);
    }

    @Test
    void register_ageLessMax_Ok() {
        testUser.setLogin("Elvis3");
        testUser.setAge(MAX_AGE - 1);
        actual = registrationService.register(testUser);
        assertTrue(MAX_AGE > MIN_AGE);
        assertEquals(testUser, actual);
    }

    @Test
    void register_ageLessMin_NotOk() {
        testUser.setLogin("youngElvis");
        testUser.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageMoreMax_NotOk() {
        testUser.setLogin("oldElvis");
        testUser.setAge(MAX_AGE + 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordLenLessMin_notOk() {
        testUser.setPassword("55555");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordLenMoreMin_Ok() {
        testUser.setPassword("1234567");
        actual = registrationService.register(testUser);
        assertTrue(MAX_AGE > MIN_AGE);
        assertEquals(testUser, actual);
    }

    @Test
    void register_nullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
