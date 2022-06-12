
package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Evgenii");
        user.setAge(34);
        user.setPassword("1234567");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_blankLogin_NotOk() {
        user.setLogin("   ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_lessPasswordLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_lessAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_existLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("Evgenii");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }
}
