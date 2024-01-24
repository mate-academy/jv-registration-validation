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
        actual = new User();
        actual.setLogin("validLogin");
        actual.setPassword("validPassword");
        actual.setAge(20);
    }

    @Test
    void register_passwordLength8_ok() throws RegistrationException {
        actual.setPassword("test1234");

        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_passwordLength6_ok() throws RegistrationException {
        actual.setPassword("test12");
        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_validUser_ok() throws RegistrationException {

        User expected = registrationService.register(actual);

        assertNotNull(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_shortLogin_notOk() {
        actual.setLogin("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_shortPassword_notOk() {
        actual.setPassword("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordEmpty_notOk() {
        actual.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordLength3_notOk() {
        actual.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordLength5_notOk() {
        actual.setPassword("abcdf");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_youngAge_notOk() {
        actual.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }
}
