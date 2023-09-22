package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        User user1 = new User();
        user1.setAge(17);
        user1.setLogin("12345");
        user1.setPassword("12345");

        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("123456");
        user2.setPassword("123456");

        User user3 = new User();
        user3.setAge(19);
        user3.setLogin("1234567");
        user3.setPassword("1234567");

        User user4 = new User();
        user4.setAge(20);
        user4.setLogin("12345678");
        user4.setPassword("12345678");

        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        storageDao.add(user4);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    @Test
    void register_user_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("123456789");
        user.setPassword("123456789");

        User expected = user;
        User actual = registrationService.register(user);

        assertEquals(expected, actual);
    }

    @Test
    void register_login_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("012345");
        user.setPassword("012345");

        Integer expected = user.getLogin().length();
        Integer actual = registrationService.register(user).getLogin().length();

        assertEquals(expected, actual);
    }

    @Test
    void register_password_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("012345");
        user.setPassword("012345");

        Integer expected = user.getPassword().length();
        Integer actual = registrationService.register(user).getPassword().length();

        assertEquals(expected, actual);
    }

    @Test
    void register_age_18_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(18);
        user.setLogin("012345");
        user.setPassword("012345");

        Integer expected = user.getAge();
        Integer actual = registrationService.register(user).getAge();

        assertEquals(expected, actual);
    }

    @Test
    void register_age_25_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(25);
        user.setLogin("012345");
        user.setPassword("012345");

        Integer expected = user.getAge();
        Integer actual = registrationService.register(user).getAge();

        assertEquals(expected, actual);
    }

    @Test
    void register_null_OK() {
        User user = new User();
        user.setId(5L);
        user.setAge(18);
        user.setLogin("012345");
        user.setPassword("012345");

        User expected = user;
        User actual = registrationService.register(user);

        assertEquals(expected, actual);
    }

    @Test
    void register_user_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_login_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("12345");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin(null);
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_password_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("1234567");
        user.setPassword("12345");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(19);
        user.setLogin("1234567");
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age_17_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(17);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negative_age_17_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(-17);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age_0_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(0);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOK() {
        User user = new User();
        user.setId(5L);
        user.setAge(null);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_null_notOK() {
        assertNull(registrationService.register(null));
    }
}
