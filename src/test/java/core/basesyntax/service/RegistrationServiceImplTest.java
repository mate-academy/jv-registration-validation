package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User actual;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_passwordLength8_ok() throws RegistrationException {
        User actual = createTestUserObject();
        actual.setPassword("12345678");
        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_passwordLength6_ok() throws RegistrationException {
        User actual = createTestUserObject();
        actual.setPassword("123456");
        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_validUser_ok() throws RegistrationException {
        User actual = createTestUserObject();
        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_shortLogin_notOk() {
        User actual = createTestUserObject();
        actual.setLogin("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_shortPassword_notOk() {
        User actual = createTestUserObject();
        actual.setPassword("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordEmpty_notOk() {
        User actual = createTestUserObject();
        actual.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordLength3_notOk() {
        User actual = createTestUserObject();
        actual.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordLength5_notOk() {
        User actual = createTestUserObject();
        actual.setPassword("abcdf");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_youngAge_notOk() {
        User actual = createTestUserObject();
        actual.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @AfterEach
    void tearDown() {
        storageDao.clear();
    }

    private User createTestUserObject() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("user123");
        user.setAge(20);
        return user;
    }
}
