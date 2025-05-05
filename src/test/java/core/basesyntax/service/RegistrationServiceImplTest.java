package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser.setLogin("User123");
        validUser.setAge(25);
        validUser.setPassword("6541651684");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_successfully_Ok() {
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        StorageDao storageDao = new StorageDaoImpl();
        String actualLogin = storageDao.get(validUser.getLogin()).getLogin();
        assertEquals(validUser.getLogin(), actualLogin);
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("User123");
        userWithSameLogin.setAge(35);
        userWithSameLogin.setPassword("4659743");
        Storage.people.add(userWithSameLogin);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_userLoginLength_NotOk() {
        validUser.setLogin("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setLogin("Use");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setLogin("User1");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_userLoginLength_Ok() {
        validUser.setLogin("User12");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        Storage.people.clear();
        validUser.setLogin("User12465786");
        actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordLength_Ok() {
        validUser.setPassword("123456");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        Storage.people.clear();
        validUser.setPassword("12345678");
        actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordLength_NotOk() {
        validUser.setPassword("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("123");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("12345");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_lowerThanAgeLimit_NotOk() {
        validUser.setAge(0);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setAge(12);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });

        validUser.setAge(17);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_AgeLimit_Ok() {
        validUser.setAge(18);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_moreThanAgeLimit_Ok() {
        validUser.setAge(50);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_negativeAge_NotOk() {
        validUser.setAge(-25);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        validUser = null;
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        validUser.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }
}
