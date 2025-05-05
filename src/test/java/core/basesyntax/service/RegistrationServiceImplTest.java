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
    private final StorageDao storageDao = new StorageDaoImpl();
    private User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser.setLogin("User777");
        validUser.setAge(25);
        validUser.setPassword("7771651684");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_successfully_Ok() {
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        String actualLogin = storageDao.get(validUser.getLogin()).getLogin();
        assertEquals(validUser.getLogin(), actualLogin);
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        User checkingSameLogin = new User();
        checkingSameLogin.setLogin(validUser.getLogin());
        checkingSameLogin.setAge(37);
        checkingSameLogin.setPassword("7659743");
        Storage.people.add(checkingSameLogin);
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
        validUser.setLogin("User");
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
        validUser.setLogin("User77");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        Storage.people.clear();
        validUser.setLogin("User52475786");
        actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordLength_Ok() {
        validUser.setPassword("723556");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        Storage.people.clear();
        validUser.setPassword("77745678");
        actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void register_userPasswordLength_NotOk() {
        validUser.setPassword("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("786");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("78645");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_underAge_notOk() {
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
