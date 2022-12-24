package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static final int DEFAULT_AGE = 18;
    private static final String DEFAULT_LOGIN = "admin";
    private static final String DEFAULT_PASSWORD = "password";
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(DEFAULT_LOGIN,DEFAULT_PASSWORD,DEFAULT_AGE);
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_AgeIsNull_NotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_LoginIsNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_ShortPassword_NotOk() {
        testUser.setPassword("qwert");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_InvalidAge_NotOk() {
        testUser.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_existingUser_NotOk() {
        Storage.people.add(testUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_Ok() {
        User actual = registrationService.register(testUser);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        assertEquals(storageDao.get(testUser.getLogin()), actual);
    }

    @Test
    void register_returnUserId() {
        Long id = registrationService.register(testUser).getId();
        assertNotNull(id);
    }

    @Test
    void register_ValidUser_Ok() {
        User actual = registrationService.register(testUser);
        assertEquals(DEFAULT_PASSWORD, actual.getPassword());
        assertEquals(DEFAULT_AGE, actual.getAge());
        assertEquals(DEFAULT_LOGIN, actual.getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
