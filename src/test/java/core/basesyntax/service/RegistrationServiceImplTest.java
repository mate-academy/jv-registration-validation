package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_throwException() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "Expected RegistrationException when User equal null");
    }

    @Test
    void register_nullLogin_throwException() {
        User actual = new User(null, "private321", 41);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when login equal null");
    }

    @Test
    void register_nullPassword_throwException() {
        User actual = new User("angela", null, 29);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when password equal null");
    }

    @Test
    void register_nullAge_throwException() {
        User actual = new User("geraSike", "givemoney", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when age equal null");
    }

    @Test
    void register_validUser_ok() {
        User expected = new User("michael32", "pedro33", 23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get("michael32"));
    }

    @Test
    void register_duplicateLogin_throwException() {
        User unexpected = new User("alex213", "first431", 18);
        storageDao.add(unexpected);
        User actual = new User("alex213", "second", 21);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException if logins are equal");
    }

    @Test
    void register_loginTooShort_throwException() {
        User actual = new User("olya2", "665433", 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when login less than 6 characters");
    }

    @Test
    void register_passwordTooShort_throwException() {
        User actual = new User("SergiiLee4", "apple", 29);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when password less than 6 characters");
    }

    @Test
    void register_ageBelow18_throwException() {
        User actual = new User("angelina566", "sOCIABLE3444", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when age less than 18");
    }

    @Test
    void register_ageBelow0_throwException() {
        User actual = new User("anna566", "hjkr3444", -10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Expected RegistrationException when age negative");
    }

    @Test
    void register_edgeLoginLength_ok() {
        User expected = new User("login1", "34621234", 19);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get("login1"));
    }

    @Test
    void register_edgePasswordLength_ok() {
        User expected = new User("finally34", "111332", 23);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get("finally34"));
    }

    @Test
    void register_ageExactly18_ok() {
        User expected = new User("login54", "54522325", 18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get("login54"));
    }
}
