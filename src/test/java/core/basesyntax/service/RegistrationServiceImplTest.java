package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void userStorageInitialization() {
        registrationService = new RegistrationServiceImpl();
        user = new User(1L,"valid_login", "123456", 40);
        Storage.people.add(new User(2L,"LarsUrlich","myolddrums",60));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthPassword_ok() {
        user.setPassword("123456");
        User registerdUser = registrationService.register(user);
        assertNotNull(registerdUser);
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_maxAge_ok() {
        user.setAge(Integer.MAX_VALUE);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("John");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = new User(7L,"LarsUrlich","thellahunginjeet",19);
        Storage.people.add(existingUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(existingUser));
    }
}
