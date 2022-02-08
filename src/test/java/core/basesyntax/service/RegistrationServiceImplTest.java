package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("123456");
        user.setId(1L);
        user.setAge(24);
        user.setLogin("Alice");
    }

    @Test
    void register_UserIsNull_NotOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
        void register_UserIsLess18_NotOk() {
        user.setAge(15);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void register_PasswordIsLess6Letters_NotOk() {
        user.setPassword("12345");
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void register_AddExistingLoginUser_NotOk() {
        storageDao.add(user);
        user.setLogin("Alice");
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void register_AddExistingLoginUserUpperCase_NotOk() {
        storageDao.add(user);
        user.setLogin("aLICe");
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void registerAdd_CorrectInput_Ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
  void register_LoginValueIsNull_NotOK() {
        user.setLogin(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void register_AgeIsNull_NotOk() {
        user.setAge(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
  void register_UserNegativeAge_NotOk() {
        user.setAge(-26);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("User age can not be negative");
    }

    @Test
  void register_UserAgeOver18_Ok() {
        user.setAge(39);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
  void tearDown() {
        Storage.people.clear();
    }
}
