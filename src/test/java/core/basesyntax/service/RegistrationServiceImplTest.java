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
    private static User newUser;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        newUser = new User();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        String newUserLogin = "NewUserLogin";
        String newUserPasswordd = "NewUserPassword";
        int newUserAge = 38;
        newUser.setAge(newUserAge);
        newUser.setLogin(newUserLogin);
        newUser.setPassword(newUserPasswordd);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        newUser.setLogin(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(newUser));
    }

    @Test
    void register_blankLogin_NotOk() {
        newUser.setLogin(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_existingUser_NotOk() {
        User register = new User();
        register.setLogin("Login");
        register.setAge(25);
        register.setPassword("Password");
        storageDao.add(register);
        int hash = storageDao.hashCode();
        newUser.setLogin("Login");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertEquals(hash, storageDao.hashCode());
    }

    @Test
    void register_ageNull_NotOk() {
        newUser.setAge(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(newUser));
    }

    @Test
    void register_ageLessThan18_NotOk() {
        newUser.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        newUser.setPassword(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(newUser));
    }

    @Test
    void register_blankPassword_NotOK() {
        newUser.setPassword(" ");
        assertThrows(RuntimeException.class,() -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_NotOk() {
        newUser.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerUser_Ok() {
        User register = new User();
        register.setLogin("Login");
        register.setPassword("Password");
        register.setAge(32);
        registrationService.register(register);
        assertEquals(32, storageDao.get(register.getLogin()).getAge());
        assertEquals("Password", storageDao.get(register.getLogin()).getPassword());
        assertEquals("Login", storageDao.get(register.getLogin()).getLogin());
    }
}
