package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    private static RegistrationServiceImpl registrationServiceImpl;

    @BeforeAll
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void initialize() {
        Storage.people.clear();
        validUser1 = new User("login1", "1234567", AGE_BARELY_OK);
        validUser2 = new User("login2", "12345678", 26);
    }

    @Test
    public void registerValidUser_ok() {
        registrationServiceImpl.register(validUser1);
        Assertions.assertEquals(validUser1, storageDao.get(validUser1.getLogin()));
    }

    @Test
    public void registerLoginBlank_notOk() {
        validUser1.setLogin(BLANK_LOGIN);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerLoginWithSpaces_notOk() {
        validUser1.setLogin(LOGIN_WITH_SPACES);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerAge_notOk() {
        validUser1.setAge(AGE_NEGATIVE);
        validUser2.setAge(AGE_NOT_OK);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser2));
    }

    @Test
    public void registerAge_ok() {
        validUser1.setAge(AGE_OK);
        validUser2.setAge(AGE_BARELY_OK);
        Assertions.assertEquals(
                registrationServiceImpl.register(validUser1),
                storageDao.get(validUser1.getLogin()));
        Assertions.assertEquals(
                registrationServiceImpl.register(validUser2),
                storageDao.get(validUser2.getLogin()));
    }

    @Test
    public void registerPasswordTooShort_notOk() {
        validUser1.setPassword(SHORT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerNullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(null));
    }

    @Test
    public void registerNullLogin_notOk() {
        validUser1.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerNullPassword_notOk() {
        validUser1.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerNullAge_notOk() {
        validUser1.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }

    @Test
    public void registerExistingUser_notOk() {
        registrationServiceImpl.register(validUser1);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(validUser1));
    }
}
