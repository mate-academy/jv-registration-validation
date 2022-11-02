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

    private User firstUser;
    private User secondUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setId(133L);
        firstUser.setAge(22);
        firstUser.setLogin("login");
        firstUser.setPassword("password");
        secondUser = new User();
        secondUser.setId(133L);
        secondUser.setAge(22);
        secondUser.setLogin("secondLogin");
        secondUser.setPassword("password");
    }

    @Test
    void register_validUserOlderThen18years_ok() {
        User registeredUser = registrationService.register(firstUser);
        User userFromStorage = storageDao.get("login");
        assertEquals(firstUser, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(firstUser, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_validUser18years_ok() {
        firstUser.setLogin("user18years");
        firstUser.setAge(18);
        User registeredUser = registrationService.register(firstUser);
        User userFromStorage = storageDao.get("user18years");
        assertEquals(firstUser, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(firstUser, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_validUserPasswordSixLetters_ok() {
        firstUser.setLogin("user6letters");
        firstUser.setPassword("123456");
        User registeredUser = registrationService.register(firstUser);
        User userFromStorage = storageDao.get("user6letters");
        assertEquals(firstUser, registeredUser,
                "User returned from register method not equals the registered user");
        assertEquals(firstUser, userFromStorage, "Can't find registered user in the storage");
    }

    @Test
    void register_addTwoValidUses_ok() {
        firstUser.setLogin("firstUserLogin");
        registrationService.register(firstUser);
        User userFromStorage = storageDao.get("firstUserLogin");
        assertEquals(firstUser, userFromStorage, "Can't find first registered user in the storage");
        registrationService.register(secondUser);
        User secondUserFromStorage = storageDao.get("secondLogin");
        assertEquals(secondUser, secondUserFromStorage,
                "Can't find second registered user in the storage");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        }, "RuntimeException should be thrown if user is null");
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin("loginWithNullLogin");
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        },"RuntimeException should be thrown if login is null");
    }

    @Test
    void register_emptyLogin_notOk() {
        firstUser.setLogin("loginWithEmptyLogin");
        firstUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if login is empty");
    }

    @Test
    void register_nullId_notOk() {
        firstUser.setLogin("loginWithNullId");
        firstUser.setId(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if id is null");
    }

    @Test
    public void register_nullPassword_notOk() {
        firstUser.setLogin("loginWithNullPassword");
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if password is null");
    }

    @Test
    public void register_notValidAge_notOk() {
        firstUser.setLogin("loginWithAge17");
        firstUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if age is less then 18");
        firstUser.setLogin("loginWithZeroAge");
        firstUser.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if age is 0");
        firstUser.setLogin("loginWithLessThenZeroAge");
        firstUser.setAge(-12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if age is less then 0");
    }

    @Test
    public void register_shortPassword_notOk() {
        firstUser.setLogin("loginWithShortPassword");
        firstUser.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        }, "RuntimeException should be thrown if password is shorter then 6 characters.");
    }

    @Test
    void register_userWithSuchLogin_NotOk() {
        firstUser.setLogin("such_login");
        secondUser.setLogin("such_login");
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        }, "RuntimeException should be thrown if user with such login already exist.");
    }
}
