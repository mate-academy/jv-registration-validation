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
        user.setAge(23);
        user.setLogin("Login123");
        user.setPassword("qwerty123");
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_invalid_login_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setLogin("log");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setLogin("login");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalid_age_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setAge(-1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setAge(10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setAge(16);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_invalid_password_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setPassword("abc");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setPassword("abcdf");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
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

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_user_Ok() {
        User register = new User();
        register.setLogin("Login1");
        register.setPassword("passwd");
        register.setAge(18);
        registrationService.register(register);
        assertEquals(18, storageDao.get(register.getLogin()).getAge());
        assertEquals("passwd", storageDao.get(register.getLogin()).getPassword());
        assertEquals("Login1", storageDao.get(register.getLogin()).getLogin());

        User register1 = new User();
        register1.setLogin("Login123");
        register1.setPassword("passwd12");
        register1.setAge(20);
        registrationService.register(register1);
        assertEquals(20, storageDao.get(register1.getLogin()).getAge());
        assertEquals("passwd12", storageDao.get(register1.getLogin()).getPassword());
        assertEquals("Login123", storageDao.get(register1.getLogin()).getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
