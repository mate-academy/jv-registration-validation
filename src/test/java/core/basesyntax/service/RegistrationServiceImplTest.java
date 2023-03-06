package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private static RegistrationServiceImpl registrationServiceImpl;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
        validUser1 = new User("login1", "1234567", 25);
        validUser2 = new User("login2", "12345678", 26);
        validUser3 = new User("login3", "123456789", 27);
    }

    @Test
    void registerValidUser_Ok() {
        registrationServiceImpl.register(validUser1);
        Assertions.assertEquals(validUser1, storageDao.get(validUser1.getLogin()));
    }

    @Test
    void registerAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser3.getLogin(), validUser3.getPassword(), -10)));
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser3.getLogin(), validUser3.getPassword(), 13)));
    }

    @Test
    void registerPasswordTooShort_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser1.getLogin(), "123", validUser1.getAge())));
    }

    @Test
    void registerNullLogin_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(null, validUser1.getPassword(), validUser1.getAge())));
    }

    @Test
    void registerNullPassword_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser1.getLogin(), null, validUser1.getAge())));
    }

    @Test
    void registerNullAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser1.getLogin(), validUser1.getPassword(), null)));
    }

    @Test
    void registerExistingUser_NotOk() {
        registrationServiceImpl.register(validUser2);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser2));
    }
}
