package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        String newUserLogin = "Login123";
        String newUserPassword = "qwerty123";
        int newUserAge = 23;
        user.setAge(newUserAge);
        user.setLogin(newUserLogin);
        user.setPassword(newUserPassword);
    }

    @Test
    void register_null_user_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_null_login_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_empty_login_NotOk() {
        user.setLogin("");
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_short_login_NotOk() {
        user.setLogin("login");
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existing_user_NotOk() {
        User existingUser = new User();
        existingUser.setLogin("existinguser");
        existingUser.setPassword("password");
        existingUser.setAge(25);
        storageDao.add(existingUser);

        User user = new User();
        user.setLogin("existinguser");
        user.setPassword("newpassword");
        user.setAge(30);

        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_null_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_under_age_limit_NotOk() {
        user.setAge(11);
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zero_age_NotOk() {
        user.setAge(0);
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negative_age_NotOk() {
        user.setAge(-1);
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_null_password_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_empty_password_NotOK() {
        user.setPassword("");
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_empty_login_NotOK() {
        user.setLogin("");
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_short_password_NotOk() {
        user.setPassword("123");
        assertThrows(InvalidRegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_user_Ok() {
        User register = new User();
        register.setLogin("Login123");
        register.setPassword("Password");
        register.setAge(18);
        registrationService.register(register);
        assertEquals(18, storageDao.get(register.getLogin()).getAge());
        assertEquals("Password", storageDao.get(register.getLogin()).getPassword());
        assertEquals("Login123", storageDao.get(register.getLogin()).getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
