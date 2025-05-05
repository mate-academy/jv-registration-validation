package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("strongPass");
        user.setAge(25);
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = createValidUser();
        User result = registrationService.register(user);
        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = createValidUser();
        registrationService.register(user1);
        User user2 = createValidUser();
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = createValidUser();
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginWithSpacesOnly_notOk() {
        User user = createValidUser();
        user.setLogin("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createValidUser();
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordWithSpacesOnly_notOk() {
        User user = createValidUser();
        user.setPassword("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_notOk() {
        User user = createValidUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAge_ok() {
        User user = createValidUser();
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        User user = createValidUser();
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
