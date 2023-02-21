package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "12345";
    private static final String INVALID_PASSWORD = "1234567";
    private static RegistrationService registrationService = new RegistrationServiceImpl();

    @Before
    void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(1L, "Bob", VALID_PASSWORD, 0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(1L, null, INVALID_PASSWORD, 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User user = new User(1L, "Ivan ivanov", INVALID_PASSWORD, 21);
        Storage.people.add(user);
        User userReg = new User(1L, "Ivan ivanov", INVALID_PASSWORD, 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(userReg));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(1L, "Jane", VALID_PASSWORD, 22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        User user = new User(1L, "Nike", VALID_PASSWORD, 15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validData() {
        int size = Storage.people.size();
        User user = new User(1L, "Nike", INVALID_PASSWORD, 22);
        registrationService.register(user);
        assertEquals(size + 1, Storage.people.size());
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }
}
