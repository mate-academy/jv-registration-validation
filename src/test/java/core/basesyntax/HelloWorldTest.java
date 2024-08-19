package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    private RegistrationServiceImpl registrationService;
    private User userLoginIsNull;
    private User userLoginIsShorterThan6;
    private User userPasswordIsNull;
    private User userPasswordIsShorterThan6;
    private User userAgeIsNull;
    private User userAgeIsSmallestThan18;
    private User checkExistingLogin1;
    private User checkExistingLogin2;
    private User validUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        userLoginIsNull = new User(null, "1234567", 19);
        userLoginIsShorterThan6 = new User("Oleks", "1234567", 19);
        userPasswordIsNull = new User("Oleksandr", null, 19);
        userPasswordIsShorterThan6 = new User("Oleksandr", "12345", 19);
        userAgeIsNull = new User("Oleksandr", "1234567", null);
        userAgeIsSmallestThan18 = new User("Oleksandr", "1234567", 17);
        checkExistingLogin1 = new User("Oleksandr", "12345678", 18);
        checkExistingLogin2 = new User("Oleksandr", "12345678", 18);
        validUser = new User("ValidUser", "12345678", 18);
    }

    @Test
    void register_existLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(checkExistingLogin1);
            registrationService.register(checkExistingLogin2);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginIsNull);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginIsShorterThan6);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordIsNull);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordIsShorterThan6);
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeIsNull);
        });
    }

    @Test
    void register_underageUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeIsSmallestThan18);
        });
    }

    @Test
    void register_validUser_ok() {
        assertDoesNotThrow(() -> {
            registrationService.register(validUser);
        });
    }
}
