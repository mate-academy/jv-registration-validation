package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    static RegistrationService registrationService;
    static StorageDao storageDao;
    static Exception ExpectedException;
    static User user1;
    static User user2;
    static User user3;
    User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        ExpectedException = new RuntimeException();
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user1.setLogin("user1");
        user1.setPassword("123456");
        user1.setAge(18);
        user2.setLogin("user2");
        user2.setPassword("abcdef");
        user2.setAge(40);
        user3.setLogin("user3");
        user3.setPassword("5hy9dd");
        user3.setAge(30);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);

        user = new User();
        user.setLogin("okname");
        user.setPassword("okpassword");
        user.setAge(20);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        user.setLogin(null);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_onlyWhitespacesLogin_Ok() {
        user.setLogin("     ");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_onlySpecialCharsLogin_Ok() {
        user.setLogin("#$%^&*)!(");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-1);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lessThan18Age_NotOk() {
        user.setAge(17);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_IntegerMinValueAge_NotOk() {
        user.setAge(Integer.MIN_VALUE);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_18Age_Ok() {
        user.setAge(18);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_IntegerMaxValueAge_Ok() {
        user.setAge(Integer.MAX_VALUE);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lessThan5CharsPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_onlyWhitespacesPassword_Ok() {
        user.setPassword("      ");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_onlySpecialCharsPassword_Ok() {
        user.setPassword("#$%^&*)!(");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_alreadyContainingUser_NotOk() {
        storageDao.add(user);
        assertThrows(ExpectedException.getClass(), () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_correctUser_Ok() {
        User expected = user;
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }
}
