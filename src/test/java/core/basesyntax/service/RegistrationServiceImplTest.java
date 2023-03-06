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
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationServiceImpl;
    private static User userTest1;
    private static User userTest2;
    private static User userTest3;
    private static User userTest4;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
        userTest1 = new User("login1", "1234567", MIN_AGE);
        userTest2 = new User("login2", "12345678", NORMAL_AGE);
        userTest3 = new User("Login3", "123456789", 35);
        userTest4 = new User("login4", "1234567890", 40);
    }

    @BeforeEach
    void storageClear() {
        Storage.people.clear();
    }

    @Test
    void registerUser_ok() {
        registrationServiceImpl.register(userTest1);
        Assertions.assertEquals(userTest1, storageDao.get(userTest1.getLogin()));
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
                        new User(EMPTY_LOGIN, userTest3.getPassword(), NEGATIVE_AGE)));
    }

    @Test
    void registerWhitespaceLogin_notOk() {
        registrationServiceImpl.register(userTest4);
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(LOGIN_WITH_WHITESPACES, userTest4.getPassword(), NEGATIVE_AGE)));
    }

    @Test
    void registerNullLogin_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(null, userTest1.getPassword(), userTest1.getAge())));
    }

    @Test
    void registerLittleAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(userTest2.getLogin(), userTest2.getPassword(), LITTLE_AGE)));
    }

    @Test
    void registerNegativeAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(userTest3.getLogin(), userTest3.getPassword(), NEGATIVE_AGE)));
    }

    @Test
    void registerAge_ok() {
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(userTest4.getLogin(), userTest4.getPassword(), MIN_AGE)),
                storageDao.get(userTest4.getLogin()));
        Assertions.assertEquals(registrationServiceImpl.register(
                        new User(userTest1.getLogin(), userTest1.getPassword(), NORMAL_AGE)),
                storageDao.get(userTest1.getLogin()));
    }

    @Test
    void registerNullAge_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(userTest4.getLogin(), userTest4.getPassword(), null)));
    }

    @Test
    void registerShortPassword_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(userTest2.getLogin(), SHORT_PASS, userTest2.getAge())));
    }

    @Test
    void registerNullPassword_notOk() {
        Assertions.assertThrows(RegistrationIsFailedException.class,
                () -> registrationServiceImpl.register(
                        new User(userTest4.getLogin(), null, userTest4.getAge())));
    }
}
