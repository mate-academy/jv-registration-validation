package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private static User sameUser;
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        sameUser = new User();
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("qwerty");
        user.setPassword("qwerty");
        user.setAge(18);
        sameUser.setLogin("qwerty");
        sameUser.setPassword("qwerty");
        sameUser.setAge(18);
    }

    @Test
    void register_nullAge_notOk() {
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
        user.setPassword(null);
        String actual = user.getPassword();
        assertNull(actual);
    }

    @Test
    void register_lengthPassword_Ok() {
        int length = user.getPassword().length();
        boolean actual = false;
        if (length >= MIN_PASSWORD_LENGTH) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_lengthLogin_NotOk() {
        int length = user.getLogin().length();
        boolean actual = false;
        if (length >= MIN_PASSWORD_LENGTH) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_age_NotOk() {
        User user2 = new User();
        user2.setLogin("qwerty");
        user2.setPassword("qwerty");
        user2.setAge(17);
        int actual1 = user.getAge();
        int actual2 = user2.getAge();
        int expected = 18;
        assertEquals(expected, actual1);
        assertNotEquals(expected, actual2);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_LoginTooShort_NotOk() {
        user.setLogin("qwert");

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_PasswordLessSixSymbols_NotOk() {
        user.setPassword("qwert");

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AgeLessMinimum_NotOk() {
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AddUser_OK() {
        boolean sameLogin = user.getLogin().equals(sameUser.getLogin());
        assertTrue(sameLogin);
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(sameUser);
        });
    }

    @Test
    void register_sameUsers_NotOk() {
        boolean differenceUsers = user.getLogin().equals(sameUser.getLogin());
        assertTrue(differenceUsers);
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(sameUser);
        });
    }

    @Test
    void register_DifferentUsers_Ok() {
        User notSameUser = new User();
        notSameUser.setLogin("qwertyyy");
        notSameUser.setPassword("qwerty");
        notSameUser.setAge(19);
        boolean differentLogin = user.getLogin()
                .equals(notSameUser.getLogin());
        assertFalse(differentLogin);

        storageDao.add(user);
        storageDao.add(notSameUser);
        assertEquals(user,storageDao.get(user.getLogin()));
        assertEquals(notSameUser, storageDao.get(notSameUser.getLogin()));
    }
}
