package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(12345678L);
        user.setLogin("dopman3000");
        user.setPassword("1234567");
        user.setAge(20);
    }

    @Test
    void register_userIsValid_Ok() {
        User registeredUser = registrationService.register(user);
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_idIsNull_NotOk() {
        user.setId(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordInvalidLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
