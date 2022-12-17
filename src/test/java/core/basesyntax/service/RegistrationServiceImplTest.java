package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_incorrectAge_NotOk() {
        User user = new User("qwertyd", "1234567", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User("qwertyd", "12345", 20);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectLogin_NotOk() {
        User user = new User("Ukraients_Oleksii", "123456", 20);
        Storage.people.add(user);
        User userFromRegister = new User("Ukraients_Oleksii", "123456", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userFromRegister));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, "1234567", 20);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User("qwertyd", "1234567", null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User("qwertyd", null, 20);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validData_Ok() {
        int expectedStorageSize = Storage.people.size() + 1;
        User user = new User("qwertyd", "1234567", 20);
        registrationService.register(user);
        assertEquals(expectedStorageSize, Storage.people.size());
        assertEquals(user, Storage.people.get(Storage.people.size() - 1));
    }
}
