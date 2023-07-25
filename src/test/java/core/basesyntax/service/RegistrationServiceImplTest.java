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
    }

    @Test
    void register_userWasPutInList_Ok() {
        registrationService.register(validUser);
        StorageDao storageDao = new StorageDaoImpl();
        String actual = storageDao.get(validUser.getLogin()).getLogin();
        assertEquals(validUser.getLogin(), actual);
    }

    @Test
    void register_returnCorrectUser_Ok() {
        String actual = registrationService.register(validUser).getLogin();
        assertEquals(validUser.getLogin(), actual);
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        Storage.people.add(validUser);
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
    void register_userLoginExactLength_Ok() {
        validUser.setLogin("User12");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userLoginOverLength_Ok() {
        validUser.setLogin("User12465786");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordExactLength_Ok() {
        validUser.setPassword("123456");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordOverLength_Ok() {
        validUser.setPassword("1234567890");
        User actual2 = registrationService.register(validUser);
        assertNotNull(actual2);
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
        validUser.setAge(15);
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
