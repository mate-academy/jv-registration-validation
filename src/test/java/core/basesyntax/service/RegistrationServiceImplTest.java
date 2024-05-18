package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final Integer DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_LOGIN = "Ukraine";
    private User defaultUser;

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
    void ageElementIsNull_NotOk() {
        User actual = defaultUser;
        actual.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void passwordElementIsNull_NotOk() {
        User actual = defaultUser;
        actual.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void loginElementIsNull_NotOk() {
        User actual = defaultUser;
        actual.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void invalidLoginElement_NotOk() {
        User actual = defaultUser;
        actual.setLogin("asd");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void invalidAgeElement_NotOk() {
        User actual = defaultUser;
        actual.setAge(1);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setAge(5);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void invalidPasswordElement_NotOk() {
        User actual = defaultUser;
        actual.setPassword("Tom");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
        actual.setPassword("Alex");
        assertThrows(RegistrationException.class, () -> registrationService.register(actual));
    }

    @Test
    void allRegistrationElement_Ok() throws RegistrationException {
        User actual = new User();
        actual.setLogin("chuga1ster");
        actual.setPassword("12345678");
        actual.setAge(25);
        User expected = new User();
        expected.setLogin("chuga1ster");
        expected.setPassword("12345678");
        expected.setAge(25);
        assertEquals(expected, registrationService.register(actual));
    }
}
