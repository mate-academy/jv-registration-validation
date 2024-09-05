package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static RegistrationService registrationService;
    private static final int DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "Ukraine";
    private User defaultUser;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setAge(DEFAULT_AGE);
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setLogin(DEFAULT_LOGIN);
    }

    @AfterEach
    void clearAfterTest() {
        Storage.people.clear();
    }

    @Test
    void register_NullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        User actual = defaultUser;
        actual.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_NullLogin_NotOk() {
        User actual = defaultUser;
        actual.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_InvalidLogin_NotOk() {
        User actual = defaultUser;
        actual.setLogin("asd");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_InvalidAge_NotOk() {
        User actual = defaultUser;
        actual.setAge(1);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setAge(5);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_InvalidPassword_NotOk() {
        User actual = defaultUser;
        actual.setPassword("Tom");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setPassword("Alex");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_NegativeAge_NotOk() {
        User actual = defaultUser;
        actual.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_UserAlreadyExists_NotOk() throws RegistrationException {
        registrationService.register(defaultUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_ValidUser_Ok() throws RegistrationException {
        User expected = new User();
        expected.setLogin("chuga1ster");
        expected.setPassword("12345678");
        expected.setAge(25);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }
}
