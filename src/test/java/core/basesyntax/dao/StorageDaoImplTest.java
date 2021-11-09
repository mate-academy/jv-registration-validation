package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        Storage.people.clear();
        user1 = new User();
        user2 = new User();
        user1.setAge(23);
        user1.setLogin("mylogin");
        user1.setPassword("SuperPassword666");
        user2.setAge(40);
        user2.setLogin("Vasya81");
        user2.setPassword("Password81");
    }

    @Test
    void register_LoginAlreadyExists_notOk() {
        user1.setLogin("Vasya81");
        registrationService.register(user1);
        try {
            assertNotEquals(user2, registrationService.register(user2));
        } catch (AssertionError e) {
            throw new RuntimeException("Login already exists", e);
        }
    }

    @Test
    void register_loginIsNull_notOk() {
        user1.setLogin(null);
        try {
            assertNotEquals(user1, registrationService.register(user1));
        } catch (AssertionError e) {
            throw new RuntimeException("Login is null", e);
        }
    }

    @Test
    void register_loginIsShorterThan6_notOk() {
        user1.setLogin("login");
        try {
            assertNotEquals(user1, registrationService.register(user1));
        } catch (AssertionError e) {
            throw new RuntimeException("Login is shorter than 6", e);
        }
    }

    @Test
    void register_loginNotOnlyLettersNumbers_notOk() {
        user1.setLogin("log!@#$%^&*()<>?in");
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("log!@#$%^&*()<>?in"));
        } catch (AssertionError e) {
            throw new RuntimeException("Login should contain letters and numbers only", e);
        }
    }

    @Test
    void register_ageIsLegal_ok() {
        registrationService.register(user1);
        assertEquals(user1, storageDao.get("mylogin"));
    }

    @Test
    void register_ageIsIllegal_notOk() {
        user1.setAge(15);
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is less than allowed", e);
        }
    }

    @Test
    void register_ageIsFantastic_notOk() {
        user1.setAge(120);
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is higher than allowed", e);
        }
    }

    @Test
    void register_ageIsNegative_notOk() {
        user1.setAge(-3);
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is negative", e);
        }
    }

    @Test
    void register_ageIsNull_notOk() {
        user1.setAge(null);
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Age is null", e);
        }
    }

    @Test
    void register_pwdIsShorterThan6_notOk() {
        user1.setPassword("Pass1");
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Password should be not less than 6 symbols", e);
        }
    }

    @Test
    void register_pwdIsNotShorterThan6_ok() {
        registrationService.register(user1);
        assertEquals(user1, storageDao.get("mylogin"));
    }

    @Test
    void register_pwdNotOnlyLettersDigitsCaps_notOk() {
        user1.setPassword("Pass10!@#$%^&*()><?");
        user2.setPassword("+=_:;dek83F");
        registrationService.register(user1);
        registrationService.register(user2);
        try {
            assertTrue(storageDao.get("mylogin") == null
                    && storageDao.get("Vasya81") == null);
        } catch (AssertionError e) {
            throw new RuntimeException("Password should contain letters and numbers only", e);
        }
    }

    @Test
    void register_pwdNoCapsAndNumbers_notOk() {
        user1.setPassword("password1");
        user2.setPassword("PassWord");
        registrationService.register(user1);
        registrationService.register(user2);
        try {
            assertTrue(storageDao.get("mylogin") == null
                    && storageDao.get("Vasya81") == null);
        } catch (AssertionError e) {
            throw new RuntimeException("Password should contain caps and numbers", e);
        }
    }

    @Test
    void register_PwdIsNull_notOk() {
        user1.setPassword(null);
        registrationService.register(user1);
        try {
            assertNull(storageDao.get("mylogin"));
        } catch (AssertionError e) {
            throw new RuntimeException("Password is null", e);
        }
    }

    @Test
    void register_allFieldsAre_Ok() {
        registrationService.register(user1);
        registrationService.register(user2);
        assertTrue(user1.equals(storageDao.get("mylogin"))
                && user2.equals(storageDao.get("Vasya81")));
    }
}