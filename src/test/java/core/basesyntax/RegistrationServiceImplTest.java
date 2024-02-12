package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void justAddingFitUsers_Ok() {
        registrationService.register(new User("validLogin", "validPass", 18));
        registrationService.register(new User("ValidLogin@gmail.com", "Valid_pass98", 18));
        registrationService.register(new User("validlogin_98", "valid_Pass3", 65));
    }

    @Test
    void loginNull_NotOK() {
        User userNullLogin = new User(null, "validPass", 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLogin));
    }

    @Test
    void passwordNull_NotOK() {
        User userNullPassword = new User("validLogin", null, 25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void ageNull_NotOK() {
        User userNullAge = new User("validLogin", "validPass", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAge));
    }

    @Test
    void ageLess_NotOK() {
        User userUnderage = new User("validLogin", "validPass", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userUnderage));
    }

    @Test
    void loginLess_NotOK() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("", "validPass", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("abc", "validPass", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("abcdf", "validPass", 18)));
    }

    @Test
    void passwordLess_NotOK() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "abc", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "abcdf", 18)));
    }

    @Test
    void userIsRegistered_NotOK() {
        User user = new User("validLogin2", "validPass", 60);
        registrationService.register(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
