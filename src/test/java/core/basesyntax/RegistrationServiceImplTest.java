package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final Object NULL_VALUE = null;
    private static final String UNREAL_LOGIN1 = "nuefdd";
    private static final String UNREAL_LOGIN2 = "dfghjkl";
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pass";
    private static final String LOGIN1 = "login1";
    private static final String LOGIN2 = "login2";
    private static final String LOGIN3 = "login3";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private static final String PASSWORD3 = "password3";
    private static final int AGE1 = 22;
    private static final int AGE2 = 40;
    private static final int AGE3 = 18;
    private static final int SMALL_AGE = 12;
    private static final int UNREAL_AGE = -10;
    private static final long USER_ID1 = 1L;
    private static final long USER_ID2 = 2L;
    private static final long USER_ID3 = 3L;
    private static RegistrationService registrationService;
    private static StorageDao storage;
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user1 = new User(USER_ID1,LOGIN1, PASSWORD1, AGE1);
        user2 = new User(USER_ID2,LOGIN2, PASSWORD2, AGE2);
        user3 = new User(USER_ID3,LOGIN3, PASSWORD3, AGE3);
    }

    @Test
    void registerAndGetOrdinaryUsers() {
        registrationService.register(user1);
        User actual = storage.get(LOGIN1);
        assertEquals(user1,actual);

        registrationService.register(user2);
        actual = storage.get(LOGIN2);
        assertEquals(user2,actual);

        registrationService.register(user3);
        actual = storage.get(LOGIN3);
        assertEquals(user3,actual);
    }

    @Test
    void getUserByUnrealLogin() {
        assertEquals(NULL_VALUE, storage.get(UNREAL_LOGIN1));

        assertEquals(NULL_VALUE, storage.get(UNREAL_LOGIN2));
    }

    @Test
    void registerAndGetUnrealUsers() {
        User actual = storage.get(LOGIN1);
        assertNotEquals(user2,actual);

        actual = storage.get(LOGIN2);
        assertNotEquals(user3,actual);

        actual = storage.get(LOGIN3);
        assertNotEquals(user1,actual);
    }

    @Test
    void registerNullUser() {
        User userNull = (User) NULL_VALUE;
        Assertions.assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userNull);
        }, "User mustn`t be null");
    }

    @Test
    void registerUserWithNullLogin() {
        User userWithLogin = new User(USER_ID1, (String) NULL_VALUE, PASSWORD, AGE2);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLogin);
        }, "User login mustn`t be null");
    }

    @Test
    void registerUserWithNullPassword() {
        User userWithPassword = new User(USER_ID1, LOGIN, (String) NULL_VALUE, AGE1);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithPassword);
        }, "User password mustn`t be null");
    }

    @Test
    void registerUserWithAllUnrealValues() {
        User userWithLoginAndPasswordAndAge = new User(USER_ID1, (String) NULL_VALUE,
                (String) NULL_VALUE, UNREAL_AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLoginAndPasswordAndAge);
        }, "User have invalid value");
    }

    @Test
    void registerUserWithNullPasswordAndLogin() {
        User userWithLoginAndPassword = new User(USER_ID1, (String) NULL_VALUE,
                (String) NULL_VALUE, AGE2);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLoginAndPassword);
        }, "User login or password mustn`t be null");
    }

    @Test
    void registerUserWithShotPassword() {
        User userWithShortPassword = new User(USER_ID1, LOGIN, SHORT_PASSWORD, AGE2);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithShortPassword);
        }, "User password must be longer than 6 elements");
    }

    @Test
    void registerUserWithBadAge() {
        User userWithBadAge = new User(USER_ID1, LOGIN, PASSWORD, SMALL_AGE);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithBadAge);
        }, "User age must be bigger than 18");
    }

    @Test
    void registerTheSameUsers() {
        assertEquals(user1, storage.get(LOGIN1));

        user2 = user1;
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(user2);
        }, "User is already present");
    }
}
