package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_BARELY_OK = 18;
    private static final int AGE_OK = 25;
    private static final int AGE_NEGATIVE = -10;
    private static final int AGE_NOT_OK = 13;
    private static final String SHORT_PASSWORD = "123";
    private static final String BLANK_LOGIN = "";
    private static final String LOGIN_WITH_SPACES = "123123  ";
    private static StorageDao storageDao;
    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private static User validUser4;
    private static RegistrationServiceImpl registrationServiceImpl;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
        validUser1 = new User("login1", "1234567", AGE_BARELY_OK);
        validUser2 = new User("login2", "12345678", 26);
        validUser3 = new User("login3", "123456789", 27);
        validUser4 = new User("login4", "1234567890", 28);
    }

    @Test
    void registerValidUser_Ok() {
        registrationServiceImpl.register(validUser1);
        Assertions.assertEquals(validUser1, storageDao.get(validUser1.getLogin()));
    }
    @Test
    void registerLoginBlank_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(BLANK_LOGIN, validUser3.getPassword(), AGE_NEGATIVE)));
    }
    @Test
    void registerLoginWithSpaces_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(LOGIN_WITH_SPACES, validUser3.getPassword(), AGE_NEGATIVE)));
    }

    @Test
    void registerAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser3.getLogin(), validUser3.getPassword(), AGE_NEGATIVE)));
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser3.getLogin(), validUser3.getPassword(), AGE_NOT_OK)));
    }

    @Test
    void registerAge_Ok() {
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(validUser2.getLogin(), validUser2.getPassword(), AGE_BARELY_OK)),
                storageDao.get(validUser2.getLogin()));
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(validUser3.getLogin(), validUser3.getPassword(), AGE_OK)),
                storageDao.get(validUser3.getLogin()));
    }

    @Test
    void registerPasswordTooShort_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(
                new User(validUser1.getLogin(), SHORT_PASSWORD, validUser1.getAge())));
    }

    @Test
    void registerNullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(null));
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
        registrationServiceImpl.register(validUser4);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser4));
    }
}
