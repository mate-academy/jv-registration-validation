package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(null));
    }

    @Test
    void register_NullAge_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("validpassword");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SmallAge_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("validpassword");
        user.setAge(10);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Age18_Ok() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("validpassword1");
        user.setAge(18);
        try {
            assertEquals(user, registrationService.register(user));
        } catch (RegistrationServiceException e) {
            fail(e);
        }
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_NegativeAge_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("validpassword");
        user.setAge(-18);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOk() {
        User user = new User();
        user.setPassword("validpassword");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("validpassword");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ShortLogin_NotOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("validpassword");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Login6Symbols_Ok() {
        User user = new User();
        user.setLogin("valid1");
        user.setPassword("validpassword");
        user.setAge(20);
        try {
            assertEquals(user, registrationService.register(user));
        } catch (RegistrationServiceException e) {
            fail(e);
        }
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_EmptyPassword_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Password3Symbols_NotOk() {
        User user = new User();
        user.setLogin("validlogin");
        user.setPassword("abc");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Password6Symbols_Ok() {
        User user = new User();
        user.setLogin("validlogin1");
        user.setPassword("valid1");
        user.setAge(20);
        try {
            assertEquals(user, registrationService.register(user));
        } catch (RegistrationServiceException e) {
            fail(e);
        }
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_ExistingUser_NotOk() {
        User user = new User();
        user.setLogin("validlogin3");
        user.setPassword("validpassword3");
        user.setAge(20);
        storageDao.add(user);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_ValidUser_Ok() {
        User user = new User();
        user.setLogin("val$$idlo22gin1");
        user.setPassword("val*&^id1pass");
        user.setAge(38);
        try {
            assertEquals(user, registrationService.register(user));
        } catch (RegistrationServiceException e) {
            fail(e);
        }
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
