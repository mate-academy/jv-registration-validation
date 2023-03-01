package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long USER_ID = 145789L;
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_FOR_SAME_USER = "passwordsame";
    private static final String SHORT_PASSWORD = "pass";
    private static final int AGE = 22;
    private static final int AGE_FOR_SAME_USER = 40;
    private static final int SMALL_AGE = 12;
    private static final int UNREAL_AGE = -10;
    private static RegistrationService registrationService;
    private static StorageDao storage;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(USER_ID,LOGIN, PASSWORD, AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerAndGet_ordinaryUsers_isOk() {
        registrationService.register(testUser);
        User actual = storage.get(LOGIN);
        assertEquals(testUser,actual);
    }

    @Test
    void register_nullUser_isNotOk() {
        User userNull = null;
        Assertions.assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userNull);
        },"Expected " + UserIsNotValidException.class.getName()
                + " to be thrown for the null user, but it wasn't");
    }

    @Test
    void register_userWithNullLogin_isNotOk() {
        testUser.setLogin(null);
        testUser.setPassword(PASSWORD);
        testUser.setAge(AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user login, but it wasn't");
    }

    @Test
    void register_userWithNullPassword_isNotOk() {
        testUser.setLogin(LOGIN);
        testUser.setPassword(null);
        testUser.setAge(AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user password, but it wasn't");
    }

    @Test
    void register_userWithAllUnrealValues_isNotOk() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(UNREAL_AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "to be thrown for the null user login and password and unreal age, but it wasn't ");
    }

    @Test
    void register_userWithNullPasswordAndLogin_isNotOk() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user login and password, but it wasn't");
    }

    @Test
    void register_userWithShotPassword_isNotOk() {
        testUser.setLogin(LOGIN);
        testUser.setPassword(SHORT_PASSWORD);
        testUser.setAge(AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the short user password, but it wasn't");
    }

    @Test
    void register_userWithInvalidAge_isNotOk() {
        testUser.setLogin(LOGIN);
        testUser.setPassword(PASSWORD);
        testUser.setAge(SMALL_AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the small user age, but it wasn't");
    }

    @Test
    void register_theSameUsers_isNotOk() {
        registrationService.register(testUser);
        assertEquals(testUser, storage.get(LOGIN));

        User sameTestUser = testUser;
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(sameTestUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the same user, but it wasn't");
    }

    @Test
    void register_userWithAlreadyExistingLogin_throwsException() {
        registrationService.register(testUser);
        assertEquals(testUser, storage.get(LOGIN));

        User sameTestUser = testUser;
        sameTestUser.setPassword(PASSWORD_FOR_SAME_USER);
        sameTestUser.setAge(AGE_FOR_SAME_USER);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(sameTestUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the same user login, but it wasn't");
    }
}
