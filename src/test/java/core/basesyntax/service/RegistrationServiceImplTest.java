package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        User user3 = new User();
        user3.setAge(19);
        user3.setLogin("1234567");
        user3.setPassword("1234567");

        storageDao.add(user3);
    }

    @Test
    void register_OK() {
        User user = new User();
        user.setAge(18);
        user.setLogin("012345");
        user.setPassword("012345");

        User expectedUser = user;
        User actualUser = registrationService.register(user);
        assertEquals(expectedUser, actualUser);
        Storage.people.clear();

        Integer expectedLoginLength = user.getLogin().length();
        Integer actualLoginLength = registrationService.register(user).getLogin().length();
        assertEquals(expectedLoginLength, actualLoginLength);
        Storage.people.clear();

        Integer expectedPasswordLength = user.getPassword().length();
        Integer actualPasswordLength = registrationService.register(user).getPassword().length();
        assertEquals(expectedPasswordLength, actualPasswordLength);
        Storage.people.clear();

        Integer expectedAge = user.getAge();
        Integer actualAge = registrationService.register(user).getAge();
        assertEquals(expectedAge, actualAge);
        Storage.people.clear();

        user.setAge(25);
        expectedAge = user.getAge();
        actualAge = registrationService.register(user).getAge();
        assertEquals(expectedAge, actualAge);
    }

    @Test
    void register_user_notOK() {
        User user = new User();
        user.setAge(19);
        user.setLogin("1234567");
        user.setPassword("1234567");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOK() {
        User user = new User();
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
