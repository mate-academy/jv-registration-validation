package core.basesyntax.service;

import core.basesyntax.RegistrationIsFailedException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int NEGATIVE_AGE = -18;
    private static final int LITTLE_AGE = 12;
    private static final int NORMAL_AGE = 30;
    private static final String EMPTY_LOGIN = "";
    private static final String LOGIN_WITH_WHITESPACES = "Lo gi n ";
    private static final String SHORT_PASS = "abc";
    private static final String FIRST_USER_PASS = "1234567";
    private static final String SECOND_USER_PASS = "12345678";
    private static final String FIRST_USER_LOGIN = "login1";
    private static final String SECOND_USER_LOGIN = "login2";
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationServiceImpl;
    private static User userTest1;
    private static User userTest2;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
        userTest1 = new User(FIRST_USER_LOGIN, FIRST_USER_PASS, MIN_AGE);
        userTest2 = new User(SECOND_USER_LOGIN, SECOND_USER_PASS, NORMAL_AGE);
    }

    @BeforeEach
    void storageClear() {
        Storage.people.clear();
    }

    @Test
    void registerUser_ok() {
        registrationServiceImpl.register(userTest1);
        Assertions.assertEquals(userTest1, storageDao.get(FIRST_USER_LOGIN));
    }

    @Test
    void registerNullUser_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(null));
    }

    @Test
    void registerExistingUser_notOk() {
        registrationServiceImpl.register(userTest2);
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(userTest2));
    }

    @Test
    void registerEmptyLogin_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(EMPTY_LOGIN, SECOND_USER_PASS, NEGATIVE_AGE)));
    }

    @Test
    void registerWhitespaceLogin_notOk() {
        registrationServiceImpl.register(userTest1);
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(LOGIN_WITH_WHITESPACES, FIRST_USER_PASS, NEGATIVE_AGE)));
    }

    @Test
    void registerNullLogin_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(null, FIRST_USER_PASS, MIN_AGE)));
    }

    @Test
    void registerLittleAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(SECOND_USER_LOGIN, SECOND_USER_PASS, LITTLE_AGE)));
    }

    @Test
    void registerNegativeAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(FIRST_USER_LOGIN, FIRST_USER_LOGIN, NEGATIVE_AGE)));
    }

    @Test
    void registerAge_ok() {
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(SECOND_USER_LOGIN, SECOND_USER_PASS, MIN_AGE)),
                storageDao.get(SECOND_USER_LOGIN));
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(FIRST_USER_LOGIN, FIRST_USER_PASS, NORMAL_AGE)),
                storageDao.get(FIRST_USER_LOGIN));
    }

    @Test
    void registerNullAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(SECOND_USER_LOGIN, SECOND_USER_PASS, null)));
    }

    @Test
    void registerShortPassword_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(SECOND_USER_LOGIN, SHORT_PASS, NORMAL_AGE)));
    }

    @Test
    void registerNullPassword_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(FIRST_USER_LOGIN, null, NORMAL_AGE)));
    }
}
