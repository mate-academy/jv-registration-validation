package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User("validLogin", "password", 18);
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_shortLogin_notOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("short", "password", 18)));
    }

    @Test
    void register_shortPassword_notOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("login", "short", 18)));
    }

    @Test
    void register_underage_notOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("login", "password", 17)));
    }
}
