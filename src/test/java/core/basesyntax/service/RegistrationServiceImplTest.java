package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static User user;
    private static User anotherUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        anotherUser = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        anotherUser.setId(44L);
        anotherUser.setAge(32);
        anotherUser.setLogin("anotherUserLogin");
        anotherUser.setPassword("anotherUserPassword");
        user.setId(1L);
        user.setAge(23);
        user.setLogin("uniqueLogin");
        user.setPassword("uniquePassword");
    }

    @AfterEach
    void tearDown() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_NullId_NotOk() {
        user.setId(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NegativeId_NotOk() {
        user.setId(-1L);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidPasswordLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        anotherUser.setPassword("1234");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(anotherUser);
        });
    }

    @Test
    void register_SameUser_NotOk() {
        User sameUser = user;
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameUser);
        });
    }

    @Test
    void register_UsersWithSameLogin_NotOk() {
        storageDao.add(user);
        anotherUser.setLogin(user.getLogin());
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(anotherUser);
        });
        assertEquals(storageDao.get(user.getLogin()), user);
    }

    @Test
    void register_AgeLessThanNeeded_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserNegativeAge_NotOk() {
        user.setAge(-25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_TwoDifferentUsers_Ok() {
        boolean login = user.getLogin().equals(anotherUser.getLogin());
        assertFalse(login);
        storageDao.add(user);
        storageDao.add(anotherUser);
        assertNotEquals(storageDao.get(user.getLogin()), storageDao.get(anotherUser.getLogin()));
    }

    @Test
    void register_UserAgeMinRequirement_Ok() {
        user.setAge(18);
        storageDao.add(user);
    }

    @Test
    void register_UserAddition_Ok() {
        user.setAge(19);
        user.setPassword("pass12");
        user.setLogin("USER_login!5");
        User registeredUser = storageDao.add(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
