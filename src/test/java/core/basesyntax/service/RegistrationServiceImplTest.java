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
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user = new User();

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
        user.setPassword(null);
        String actual = user.getPassword();
        assertNull(actual);
    }

    @Test
    void register_lengthPassword_Ok() {
        user.setPassword("qwerty");
        int length = user.getPassword().length();
        boolean actual = false;
        if (length > 5) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_lengthLogin_NotOk() {
        user.setLogin("qwerty");
        int length = user.getLogin().length();
        boolean actual = false;
        if (length > 5) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void register_age_NotOk() {
        User user1 = new User();
        user1.setLogin("qwerty");
        user1.setPassword("qwerty");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("qwerty");
        user2.setPassword("qwerty");
        user2.setAge(17);
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
        user.setLogin("qwerty");
        user.setPassword(null);
        user.setAge(18);
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
        user.setLogin("qwerty");
        user.setPassword("qwert");
        user.setAge(18);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AgeLessMinimum_NotOk() {
        user.setLogin("qwerty");
        user.setPassword("qwerty");
        user.setAge(17);
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_AddUser_OK() {
        user.setLogin("qwerty");
        user.setPassword("qwerty");
        user.setAge(18);
        User sameUser = new User();
        sameUser.setLogin("qwerty");
        sameUser.setPassword("qwerty");
        sameUser.setAge(18);
        boolean sameLogin = user.getLogin().equals(sameUser.getLogin());
        RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
        assertTrue(sameLogin);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(sameUser);
        });
    }

    @Test
    void register_sameUsers_NotOk() {
        user.setLogin("qwerty");
        user.setPassword("qwerty");
        user.setAge(18);
        User sameUser = new User();
        sameUser.setLogin("qwerty");
        sameUser.setPassword("qwerty");
        sameUser.setAge(18);
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
        user.setLogin("qwerty");
        user.setPassword("qwerty");
        user.setAge(18);
        User notSameUser = new User();
        notSameUser.setLogin("qwertyyy");
        notSameUser.setPassword("qwerty");
        notSameUser.setAge(19);
        boolean differentLogin = user.getLogin()
                .equals(notSameUser.getLogin());
        assertFalse(differentLogin);

        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        storageDao.add(notSameUser);
        assertEquals(user,storageDao.get(user.getLogin()));
        assertEquals(notSameUser, storageDao.get(notSameUser.getLogin()));
    }
}
