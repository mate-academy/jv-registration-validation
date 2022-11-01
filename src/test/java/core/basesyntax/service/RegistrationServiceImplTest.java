package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    private User user;
    private User user2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(133L);
        user.setAge(22);
        user.setLogin("login");
        user.setPassword("password");
        user2 = new User();
        user2.setId(133L);
        user2.setAge(22);
        user2.setLogin("secondLogin");
        user2.setPassword("password");
    }

    @Test
    void register_validUserOlderThen18years_Ok() {
        User registeredUser = registrationService.register(user);
        User userFromStorage = storageDao.get("login");
        assertEquals(user, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(user, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_validUser18years_Ok() {
        user.setLogin("user18years");
        user.setAge(18);
        User registeredUser = registrationService.register(user);
        User userFromStorage = storageDao.get("user18years");
        assertEquals(user, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(user, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_validUserPasswordSixLetters_Ok() {
        user.setLogin("user6letters");
        user.setPassword("123456");
        User registeredUser = registrationService.register(user);
        User userFromStorage = storageDao.get("user6letters");
        assertEquals(user, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(user, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_addTwoValidUses_Ok() {
        user.setLogin("firstUserLogin");
        User registeredUser = registrationService.register(user);
        User userFromStorage = storageDao.get("firstUserLogin");
        assertEquals(user, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(user, userFromStorage, "Can't find registered user in the storage");
        User secondRegisteredUser = registrationService.register(user2);
        User secondUserFromStorage = storageDao.get("secondLogin");
        assertEquals(user2, secondRegisteredUser,
                "User returned from register method not equals the registered user");
        assertEquals(user2, secondUserFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        }, "RuntimeException should be thrown if user is null");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        },"RuntimeException should be thrown if login is null");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if login is empty");
    }

    @Test
    void register_nullId_notOk() {
        user.setId(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if id is null");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if password is null");
    }

    @Test
    public void register_notValidAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if age is less then 18");
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if age is 0");
        user.setAge(-12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if age is less then 0");
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if password is shorter then 6 characters.");
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "RuntimeException should be thrown if password is empty.");
    }

    @Test
    void register_userWithSuchLogin_NotOk() {
        user.setLogin("such_login");
        user2.setLogin("such_login");
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        }, "RuntimeException should be thrown if user with such login already exist.");
    }
}
