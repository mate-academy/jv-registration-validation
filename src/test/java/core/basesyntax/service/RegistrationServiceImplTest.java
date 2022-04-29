package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
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
        storageDao = new StorageDaoImpl();
        newUser = new User();
    }

    @BeforeEach
    void setUp() {
        final String newUserLogin = "LoginNew";
        final String newUserPassword = "PasswordNew";
        final int newUserAge = 29;
        newUser.setLogin(newUserLogin);
        newUser.setPassword(newUserPassword);
        newUser.setAge(newUserAge);
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userNullLogin_notOK() {
        newUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userBlankLogin_notOK() {
        newUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_userNullPassword_notOK() {
        newUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_userBlankPassword_notOK() {
        newUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_userNullAge_notOK() {
        newUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_containsAlreadyUserLogin_notOk() {
        final String registeredUserLogin = "LoginReg";
        final String registeredUserPassword = "PasswordReg";
        final int registeredUserAge = 19;
        User registeredUser = new User();
        registeredUser.setLogin(registeredUserLogin);
        registeredUser.setPassword(registeredUserPassword);
        registeredUser.setAge(registeredUserAge);
        storageDao.add(registeredUser);
        int hash = storageDao.hashCode();
        newUser.setLogin(registeredUserLogin);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertEquals(hash, storageDao.hashCode());
    }

    @Test
    void register_youngerThan18_notOk() {
        newUser.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_shortPassword_notOk() {
        newUser.setPassword("abc12");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(newUser.getLogin()));
    }

    @Test
    void register_newUser_Ok() {
        final String newRegisteredUserLogin = "LoginNewReg";
        final String newRegisteredUserPassword = "PasswordNewReg";
        final int newRegisteredUserAge = 39;
        User newRegisteredUser = new User();
        newRegisteredUser.setLogin(newRegisteredUserLogin);
        newRegisteredUser.setPassword(newRegisteredUserPassword);
        newRegisteredUser.setAge(newRegisteredUserAge);
        registrationService.register(newRegisteredUser);
        assertNotNull(storageDao.get(newRegisteredUser.getLogin()));
        assertEquals(newRegisteredUser, storageDao.get(newRegisteredUser.getLogin()));
        assertEquals("LoginNewReg", storageDao.get(newRegisteredUser.getLogin()).getLogin());
        assertEquals("PasswordNewReg", storageDao.get(newRegisteredUser.getLogin()).getPassword());
        assertEquals(39, storageDao.get(newRegisteredUser.getLogin()).getAge());
    }
}
