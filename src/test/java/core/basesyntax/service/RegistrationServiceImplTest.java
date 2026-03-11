package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidRegisterArgumentException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String LOGIN_EDGE_VALID = "user66";
    public static final String LOGIN_EDGE_INVALID = "user5";
    public static final String LOGIN_VALID = "user1234";
    public static final String LOGIN_INVALID = "usr";
    public static final String LOGIN_EMPTY = "";
    public static final String PASSWORD_EDGE_VALID = "123456";
    public static final String PASSWORD_EDGE_INVALID = "12345";
    public static final String PASSWORD_VALID = "12345678";
    public static final String PASSWORD_INVALID = "123";
    public static final String PASSWORD_EMPTY = "";
    public static final int AGE_EDGE_VALID = 18;
    public static final int AGE_VALID = 34;
    public static final int AGE_EDGE_INVALID = 17;
    public static final int AGE_INVALID = -1;
    public static final int AGE_EMPTY = 0;
    private static User testUser;
    private static StorageDao storageDao;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUpAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        Storage.people.clear();
    }

    @Test
    void registerValidArgumentsAddedToStorage_ok() {
        testUser.setLogin(LOGIN_VALID);
        testUser.setPassword(PASSWORD_VALID);
        testUser.setAge(AGE_VALID);

        User expected = testUser;

        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());

        assertEquals(expected, actual, "User was not added");
    }

    @Test
    void registerValidArgumentsReturnsCorrectUser_ok() {
        testUser.setLogin(LOGIN_VALID);
        testUser.setPassword(PASSWORD_VALID);
        testUser.setAge(AGE_VALID);

        User expected = testUser;
        User actual = registrationService.register(testUser);

        assertEquals(expected, actual, "User is different from given");
    }

    @Test
    void registerEdgeValidArguments_ok() {
        testUser.setLogin(LOGIN_EDGE_VALID);
        testUser.setPassword(PASSWORD_EDGE_VALID);
        testUser.setAge(AGE_EDGE_VALID);

        User expected = testUser;

        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());

        assertEquals(expected, actual, "User not found or is different from expected");
    }

    @Test
    void registerEdgeInvalidArguments_notOk() {
        testUser.setLogin(LOGIN_EDGE_INVALID);
        testUser.setPassword(PASSWORD_EDGE_INVALID);
        testUser.setAge(AGE_EDGE_INVALID);

        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void registerInvalidArguments_notOk() {
        testUser.setLogin(LOGIN_INVALID);
        testUser.setPassword(PASSWORD_INVALID);
        testUser.setAge(AGE_INVALID);

        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void registerEmptyArguments_notOk() {
        testUser.setLogin(LOGIN_EMPTY);
        testUser.setPassword(PASSWORD_EMPTY);
        testUser.setAge(AGE_EMPTY);

        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void registerNullArguments_notOk() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(null);

        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void registerNoArguments_notOk() {
        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void registerNullUser_notOk() {
        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(null));
    }

    @Test
    void registerExistingUser_notOk() {
        testUser.setLogin(LOGIN_VALID);
        testUser.setPassword(PASSWORD_VALID);
        testUser.setAge(AGE_VALID);

        storageDao.add(testUser);

        assertThrows(InvalidRegisterArgumentException.class,
                () -> registrationService.register(testUser));
    }
}
