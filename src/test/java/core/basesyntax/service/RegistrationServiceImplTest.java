package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User("validLogin", "validPassword", 18);
        registrationService.register(validUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_sameLogin_notOk() {
        User userWithSameLogin = new User("validLogin", "anotherPassword", 19);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithSameLogin));
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User(null, "validPassword", 18);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_shortLogin_notOk() {
        User userWithShortLogin = new User("short", "validPassword", 18);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithShortLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User("validLogin", null, 18);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithNullPassword));
    }

    @Test
    void register_shortPassword_notOk() {
        User userWithShortPassword = new User("validLogin", "short", 18);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithShortPassword));
    }

    @Test
    void register_nullAge_notOk() {
        User userWithNullAge = new User("validLogin", "validPassword", null);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(userWithNullAge));
    }

    @Test
    void register_underageUser_notOk() {
        User underageUser = new User("validLogin", "validPassword", 17);
        assertThrows(InvalidRegistrationDataException.class,
                () -> registrationService.register(underageUser));
    }

    @Test
    void register_validUser_ok() {
        User newValidUser = new User("newValidLogin", "validPassword", 20);
        assertDoesNotThrow(() -> registrationService.register(newValidUser));
    }
}
