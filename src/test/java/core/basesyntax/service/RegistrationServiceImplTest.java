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
        User actual = registrationService.register(new User("Kate123", "111111111", 19));
        assertEquals(actual, storageDao.get(actual.getLogin()));
    }
    
    @Test
    void register_anExistingLogin_NotOk() {
        Storage.people.add(new User("Bob123", "111111111", 19));
        assertThrows(ValidationException.class,() -> {
            registrationService.register(new User("Bob123", "111111111", 19));
        });
    }
    
    @Test
    void register_shortLogin_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Bob", "165498432156", 19));
        });
    }
    
    @Test
    void register_shortPassword_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Clark123", "11", 19));
        });
    }
    
    @Test
    void register_lessThan18YearsOld_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Ivan123", "419813541", 17));
        });
    }
    
    @Test
    void register_nullLogin_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User(null, "65421891", 22));
        });
    }
    
    @Test
    void register_nullPassword_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Kate987654", null, 22));
        });
    }
    
    @Test
    void register_nullAge_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Ivan987654", "111111111", null));
        });
    }
}
