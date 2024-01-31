package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static User userValid;
    private static User userInStorage;
    private static User userNull;
    private static User userLoginNull;
    private static User userLoginShort;
    private static User userLoginEmpty;
    private static User userPasswordNull;
    private static User userPasswordShort;
    private static User userAgeNull;
    private static User userAgeYoung;
    private static User userAgeLessZero;

    @BeforeAll
    static void beforeAll() {
        userValid = new User("userValid", "passwordValid", 20);
        userInStorage = new User("maxxxy", "738477hfd", 53);
        userNull = null;
        userLoginNull = new User(null, "pass1valid", 34);
        userLoginShort = new User("login", "pass4good", 34);
        userLoginEmpty = new User("   ", "pass4good", 34);
        userPasswordNull = new User("login2good", null, 23);
        userPasswordShort = new User("trevisoon", "pass", 37);
        userAgeNull = new User("login88login", "passpassword", null);
        userAgeYoung = new User("loggoodcheck", "superpass", 10);
        userAgeLessZero = new User("loggo88check", "super88pass", -20);
    }

    @Test
    void register_nullData_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNull),
                "Expected: User can`t be null");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userLoginNull),
                "Expected: The login can`t be less than... ");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userPasswordNull),
                "Expected: The password can`t be less than... ");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userAgeNull),
                "Expected: The minimum age to register is... ");
    }

    @Test
    void register_invalidLoginData_NotOK() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userLoginShort),
                "Expected: The login can`t be less than... ");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userLoginEmpty),
                "Expected: The login can`t be less than... ");
    }

    @Test
    void register_invalidPasswordData_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userPasswordShort),
                "Expected: The password can`t be less than... ");
    }

    @Test
    void register_invalidAge_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userAgeYoung),
                "Expected: The password can`t be less than... ");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userAgeLessZero),
                "Expected: The password can`t be less than... ");
    }

    @Test
    void register_goodData_Ok() {
        User actual = registrationService.register(userValid);
        assertEquals(actual, userValid, "Method return wrong data");
    }

    @Test
    void register_userConsistInStorage_notOk() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(userInStorage);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userInStorage),
                "Expected: User is already registered!");
    }
}
