package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationTests {
    private static User user;
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(111L, "userLogin", "Rn963b", 18);
        storageDao.clear();
    }

    @AfterEach
    void tearDown() {
        storageDao.clear();
    }

    @Test
    void register_UserWithValidData_OK() {
        user.setLogin("newLogin333");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_UserWithAgeLessThanMinimum_NotOK() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithAgeGreaterThanMinimum_OK() {
        user.setAge(42);
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_UserWithNegativeAge_NotOK() {
        user.setAge(-42);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithExistingLogin_NotOK() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithPasswordLessThanMinimum_NotOK() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithPasswordGreaterThanMinimum_OK() {
        user.setPassword("NewStrongPassword");
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void register_UserWithEmptyLogin_NotOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithEmptyPassword_NotOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullUser_NotOK() {
        user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullLogin() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullPassword() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullAge() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NotUserObject_NotOK() {
        assertThrows(ClassCastException.class, () ->
                registrationService.register((User) new Object()));
    }
}
