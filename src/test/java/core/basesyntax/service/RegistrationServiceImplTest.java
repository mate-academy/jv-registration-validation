package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User("John1234", "password", 23);
    }

    @Test
    void register_successful_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, storageDao.get(actual.getLogin()));
    }

    @Test
    void register_anExisting_Login_Not_Ok() {
        Storage.people.add(user);
        assertThrows(ValidationException.class,() -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Null_User_Validation() {
        user = null;
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_NotOk() {
        user.setLogin("Bob");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("123");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_litleAge_Not_Ok() {
        user.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_Not_Ok() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_Not_Ok() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(user);
        });
    }
}
