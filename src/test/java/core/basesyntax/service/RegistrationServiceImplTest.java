package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    //private static RegistrationService registrationServiceImpl;
    //private static StorageDao storageDao;
    private static User user = new User();
    //private static User differentUser;
    //private static User sameUser;

    /**@BeforeAll
    static void beforeAll() {
    RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
    StorageDao storageDao = new StorageDaoImpl();
    User user = new User();
    User differentUser = new User();
    User sameUser = new User();
    }
    }*/

    //@AfterEach
    //void tearDown() {
    //storageDao = new StorageDaoImpl();
    //}

    @Test
    void register_nullAge_notOk() {
        user.setAge(18);
        int actual = user.getAge();
        assertNotNull(actual);
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        String actual = user.getLogin();
        assertNull(actual);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user1 = new User(null, null,18);
        String actual = user1.getPassword();
        assertNull(actual);
    }

    @Test
    void register_lengthPassword_Ok() {
        User user1 = new User(null, "qwerty",18);
        int length = user1.getPassword().length();
        boolean actual = false;
        if (length > 5) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_lengthLogin_NotOk() {
        User user1 = new User("qwerty", "qwerty",18);
        int length = user1.getLogin().length();
        boolean actual = false;
        if (length > 5) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_age_NotOk() {
        User user1 = new User("qwerty", "qwerty",18);
        User user2 = new User("qwerty", "qwerty",17);
        int actual1 = user1.getAge();
        int actual2 = user2.getAge();
        int expected = 18;
        assertEquals(expected, actual1);
        assertNotEquals(expected, actual2);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_NulPassword_NotOk() {
        User user = new User("qwerty", null,18);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_LoginLessSixSymbols_NotOk() {
        user.setLogin("qwert");
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_PasswordLessSixSymbols_NotOk() {
        User user = new User("qwerty", "qwert",18);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AgeLessMinimum_NotOk() {
        User user = new User("qwerty", "qwerty",17);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AddUser_OK() {
        StorageDao storageDao = new StorageDaoImpl();
        User user = new User("qwerty", "qwerty",18);
        User sameUser = new User("qwerty", "qwerty",18);
        boolean sameLogin = user.getLogin().equals(sameUser.getLogin());
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
        assertTrue(sameLogin);
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(sameUser);
        });
    }

    @Test
    void register_sameUsers_NotOk() {
        User user = new User("qwerty", "qwerty",18);
        User sameUser = new User("qwerty", "qwerty",18);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
        boolean differenceUsers = user.getLogin().equals(sameUser.getLogin());
        StorageDao storageDao = new StorageDaoImpl();
        assertTrue(differenceUsers);
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(sameUser);
        });
    }

    @Test
    void register_DifferentUsers_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        User user = new User("qwerty", "qwerty",18);
        User notSameUser = new User("qwertyyy", "qwerty",19);
        boolean differentLogin = user.getLogin()
                .equals(notSameUser.getLogin());
        assertFalse(differentLogin);

        storageDao.add(user);
        storageDao.add(notSameUser);
        assertEquals(user,storageDao.get(user.getLogin()));
        assertEquals(notSameUser, storageDao.get(notSameUser.getLogin()));
    }
}