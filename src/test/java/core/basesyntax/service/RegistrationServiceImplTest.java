package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private int size;
    
    @BeforeEach
    void setUp() {
        Storage.people.add(new User("Bob123", "111111111", 19));
        Storage.people.add(new User("Alice123", "222222222", 22));
        Storage.people.add(new User("John123", "333333333", 25));
        size = Storage.people.size();
    }
    
    @Test
    void register_nonExistentLogin_Ok() {
        User actual = registrationService.register(new User("Kate123", "111111111", 19));
        assertEquals(size + 1, Storage.people.size());
        assertNotNull(actual);
    }
    
    @Test
    void register_anExistingLogin_NotOk() {
        assertThrows(ValidationException.class,() -> {
            registrationService.register(new User("Bob123", "111111111", 19));
        });
        assertEquals(size, Storage.people.size());
    }
    
    @Test
    void register_shortLogin_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Bob", "165498432156", 19));
        });
        assertEquals(size, Storage.people.size());
    }
    
    @Test
    void register_shortPassword_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Clark123", "11", 19));
        });
        assertEquals(size, Storage.people.size());
    }
    
    @Test
    void register_lessThan18YearsOld_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(new User("Ivan123", "419813541", 17));
        });
        assertEquals(size, Storage.people.size());
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
