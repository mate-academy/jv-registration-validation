package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserIsNotValidException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long USER_ID = 145789L;
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pass";
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(USER_ID,LOGIN, PASSWORD, MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerAndGet_ordinaryUsers_isOk() {
        User actual = registrationService.register(testUser);
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
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user login, but it wasn't");
    }

    @Test
    void register_userWithNullPassword_isNotOk() {
        testUser.setPassword(null);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user password, but it wasn't");
    }

    @Test
    void register_userWithAllUnrealValues_isNotOk() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        testUser.setAge(MIN_AGE - 1);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "to be thrown for the null user login and password and unreal age, but it wasn't ");
    }

    @Test
    void register_userWithNullPasswordAndLogin_isNotOk() {
        testUser.setLogin(null);
        testUser.setPassword(null);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the null user login and password, but it wasn't");
    }

    @Test
    void register_userWithShotPassword_isNotOk() {
        testUser.setPassword(SHORT_PASSWORD);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the short user password, but it wasn't");
    }

    @Test
    void register_userWithInvalidAge_isNotOk() {
        testUser.setAge(MIN_AGE - 1);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(testUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the small user age, but it wasn't");
    }

    @Test
    void register_userWithAlreadyExistingLogin_throwsException() {
        User actual = registrationService.register(testUser);;
        assertEquals(testUser, actual);

        User sameTestUser = testUser;
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(sameTestUser);
        }, "Expected " + UserIsNotValidException.class.getName()
                + "to be thrown for the same user login, but it wasn't");
    }
}
