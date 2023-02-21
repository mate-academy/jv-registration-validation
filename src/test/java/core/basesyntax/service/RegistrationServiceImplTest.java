package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
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
        user1 = new User(1L,"login1", "password1", 22);
        user2 = new User(2L,"login2", "password2", 20);
        user3 = new User(3L,"login3", "password3", 18);
    }

    @Test
    void registerAndGetOrdinaryUsers() {
        registrationService.register(user1);
        User actual = storage.get("login1");
        assertEquals(user1,actual);

        registrationService.register(user2);
        actual = storage.get("login2");
        assertEquals(user2,actual);

        registrationService.register(user3);
        actual = storage.get("login3");
        assertEquals(user3,actual);
    }

    @Test
    void getUserByUnrealLogin() {
        assertEquals(null, storage.get("nuefdd"));

        assertEquals(null, storage.get("dfghjkl"));
    }

    @Test
    void registerAndGetUnrealUsers() {
        User actual = storage.get("login1");
        assertNotEquals(user2,actual);

        actual = storage.get("login2");
        assertNotEquals(user3,actual);

        actual = storage.get("login3");
        assertNotEquals(user1,actual);
    }

    @Test
    void registerNullUser() {
        User userNull = null;
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userNull);
        }, "User mustn`t be null");
    }

    @Test
    void registerUserWithNullLogin() {
        User userWithLogin = new User(10L, null, "password", 48);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLogin);
        }, "User login mustn`t be null");
    }

    @Test
    void registerUserWithNullPassword() {
        User userWithPassword = new User(10L, "login", null, 48);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithPassword);
        }, "User password mustn`t be null");
    }

    @Test
    void registerUserWithAllUnrealValues() {
        User userWithLoginAndPasswordAndAge = new User(10L, null, null, -10);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLoginAndPasswordAndAge);
        }, "User have invalid value");
    }

    @Test
    void registerUserWithNullPasswordAndLogin() {
        User userWithLoginAndPassword = new User(10L, null, null, 30);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithLoginAndPassword);
        }, "User login or password mustn`t be null");
    }

    @Test
    void registerUserWithShotPassword() {
        User userWithShortPassword = new User(10L, "login", "pass", 20);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithShortPassword);
        }, "User password must be longer than 6 elements");
    }

    @Test
    void registerUserWithBadAge() {
        User userWithBadAge = new User(10L, "login", "password", 12);
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(userWithBadAge);
        }, "User age must be bigger than 18");
    }

    @Test
    void registerTheSameUsers() {
        assertEquals(user1, storage.get("login1"));

        user2 = user1;
        assertThrows(UserIsNotValidException.class, () -> {
            registrationService.register(user2);
        }, "User is already present");
    }
}