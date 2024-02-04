package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storage;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_addNonExistentUser_ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("new_user");
        user.setPassword("1234567");
        registrationService.register(user);
        User actual = storage.get(user.getLogin());
        assertEquals(user, actual, "Added user should be accessible from storage");
    }

    @Test
    void register_addExistentUser_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("new_user");
        user.setPassword("1234567");
        Storage.people.add(user);
        Throwable exception =
                assertThrows(InvalidUserDataException.class,
                        () -> registrationService.register(user),
                        "Register service should throw exception while adding same user");
        assertEquals("Login you provided already exists", exception.getMessage());
    }

    @Test
    void register_addNullValue_notOk() {
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null),
                "Null value should throw NullPointerException");
        assertEquals("User can't be null", exception.getMessage());
    }

    @Test
    void register_addUserWithNullFields_notOk() {
        User user = new User();
        user.setId(null);
        user.setAge(null);
        user.setLogin(null);
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addUserWithNullIdField_ok() {
        User user = new User();
        user.setId(null);
        user.setAge(19);
        user.setLogin("new_user");
        user.setPassword("123456");
        registrationService.register(user);
        User actualUser = storage.get(user.getLogin());
        assertEquals(user, actualUser, "User with null id field should register properly");
    }

    @Test
    void register_addUserWithAgeLess18_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("new_user");
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, age < 18");
        assertEquals("Age must be at least 18 years", exception.getMessage());
    }

    @Test
    void register_addUserWithNullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("new_user");
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, age is null");
        assertEquals("User's age can't be null", exception.getMessage());
    }

    @Test
    void register_addUserWithNullLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin(null);
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, login is null");
        assertEquals("User's login can't be null", exception.getMessage());
    }

    @Test
    void register_addUserWithNullPassword_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("new_user");
        user.setPassword(null);
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, login is null");
        assertEquals("User's password can't be null", exception.getMessage());
    }

    @Test
    void register_addUserWithLoginLess6_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("new_");
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, login length < 18");
        assertEquals("Login must contain at least 6 characters", exception.getMessage());
    }

    @Test
    void register_addUserWithPasswordLess6_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("new_user");
        user.setPassword("12345");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, password length < 18");
        assertEquals("Password must contain at least 6 characters", exception.getMessage());
    }

    @Test
    void register_addUserWithNegativeAge_notOk() {
        User user = new User();
        user.setAge(-17);
        user.setLogin("new_user");
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, age < 18");
        assertEquals("Age must be at least 18 years", exception.getMessage());
    }

    @Test
    void register_checkUserReturnsEquals_ok() {
        User expectedUser = new User();
        expectedUser.setAge(19);
        expectedUser.setLogin("new_user");
        expectedUser.setPassword("123456");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser, "Service must return registered user");
    }

    @Test
    void register_checkUserWithEmptyPassword_notOk() {
        User user = new User();
        user.setAge(18);
        user.setLogin("new_user");
        user.setPassword("");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, password length < 18");
        assertEquals("Password must contain at least 6 characters", exception.getMessage());
    }

    @Test
    void register_addUserWithEmptyLogin_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("");
        user.setPassword("123456");
        Throwable exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Service should throw InvalidUserDataException here, login length < 18");
        assertEquals("Login must contain at least 6 characters", exception.getMessage());
    }
}
