package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Test
    void register_incorrectAge_NotOk() {
        User user = new User(null, "qwertyd", "1234567", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectPassword_NotOk() {
        User user = new User(null, "qwertyd", "12345", 20);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectLogin_NotOk() {
        User user = new User(null, "Ukraients_Oleksii", "123456", 20);
        Storage.people.add(user);
        User userFromRegister = new User(null, "Ukraients_Oleksii", "123456", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userFromRegister));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, null, "1234567", 20);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(null, "qwertyd", "1234567", null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(null, "qwertyd", null, 20);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_verificationOfAddingAUserToTheDatabase_Ok() {
        int sizeListPeople = Storage.people.size();
        User user = new User(null, "qwertyd", "1234567", 20);
        registrationService.register(user);
        assertEquals(sizeListPeople + 1, Storage.people.size());
    }
}
