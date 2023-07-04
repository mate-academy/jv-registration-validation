package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_successful_Ok() {
        User actual = registrationService.register(new User("Alice123", "123456", 19));
        assertEquals(actual, storageDao.get(actual.getLogin()));
    }

    @Test
    void register_anExisting_Login_Not_Ok() {
        Storage.people.add(new User("Bob123", "111111111", 19));
        assertThrows(ValidationException.class,() -> {
            registrationService.register(new User("Bob123", "111111111", 19));
        });
    }

    @Test
    void register_shortLogin_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Bob", "1234567",20));
        });
    }

    @Test
    void register_shortPassword_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("John123","123", 21));
        });
    }

    @Test
    void register_litleAge_Not_Ok() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("John1234", "1234567", 17));
        });
    }

    @Test
    void register_nullAge_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Bob1234", "1234567", null));
        });
    }

    @Test
    void register_nullLogin_Not_Ok() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("null", "1234567", 25));
        });
    }

    @Test
    void register_nullPassword_Not_Ok() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Juliya12", "null", 23));
        });
    }
}
