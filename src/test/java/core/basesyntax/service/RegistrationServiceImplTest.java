package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int DEFAULT_AGE = 38;
    private static final String DEFAULT_PASSWORD = "password";
    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registrationService;
    private static User newUser;
    private static User existingUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        newUser = new User();
        existingUser = new User();
        existingUser.setLogin("ExistingUser");
        existingUser.setAge(DEFAULT_AGE);
        existingUser.setPassword(DEFAULT_PASSWORD);
    }

    @BeforeEach
    void setUp() {
        newUser.setLogin("login");
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(DEFAULT_AGE);
    }

    //User = null
    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    //User login
    @Test
    void register_nullUserLogin_notOk() {
        newUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        newUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userLoginIsValid_Ok() {
        String expected = newUser.getLogin();
        String actual = registrationService.register(newUser).getLogin();
        assertEquals(expected, actual);
    }

    //User password
    @Test
    void register_nullUserPassword_notOk() {
        newUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        newUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordLengthLessThanMinimumLength_notOk() {
        newUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordLengthEqualsMinimumLength_Ok() {
        newUser.setPassword("123456");
        String expected = newUser.getPassword();
        String actual = registrationService.register(newUser).getPassword();
        assertEquals(expected, actual);
    }

    @Test
    void register_userPasswordLengthGreaterThanMinimumLength_Ok() {
        newUser.setPassword("Tom&Jerry");
        String expected = newUser.getPassword();
        String actual = registrationService.register(newUser).getPassword();
        assertEquals(expected, actual);
    }

    //User age
    @Test
    void register_nullUserAge_notOk() {
        newUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeLessThanMinimumAge_notOk() {
        newUser.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        newUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeEqualsMinimumAge_Ok() {
        newUser.setAge(MIN_AGE);
        int expected = newUser.getAge();
        int actual = registrationService.register(newUser).getAge();
        assertEquals(expected, actual);
    }

    @Test
    void register_userAgeGreaterThanMinimumAge_Ok() {
        newUser.setAge(MIN_AGE + 1);
        int expected = newUser.getAge();
        int actual = registrationService.register(newUser).getAge();
        assertEquals(expected, actual);
    }

    //Register existing User
    @Test
    void register_userAlreadyExistsInTheStorage_notOk() {
        storageDao.add(existingUser);
        newUser.setLogin(existingUser.getLogin());
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
