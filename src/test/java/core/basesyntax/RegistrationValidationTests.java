package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.Test;

public class RegistrationValidationTests {

    private User validUser = new User(2556465L, "someuser", "userpass", 56);
    private User usersameAsValidUser = new User(2556465L, "someuser", "userpass", 56);
    private User userWrongPass = new User(25458965L, "someuser1", "us1pa", 25);
    private User userWrongAge = new User(25234465L, "someuser3", "user3password", 17);
    private User userWrongLogin = new User(25234465L, "user3", "user5password", 25);

    @Test
    public void duplicate_user_registration_NotOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        registrationService.register(usersameAsValidUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser),
                "Registration method should not accept user with login name used "
                        + "by previously registered users");
    }

    @Test
    public void age_of_User_NotOK() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongAge),
                "Registration of user with age under 18 should not be allowed");
    }

    @Test
    public void password_of_User_NotOK() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongPass),
                "Registration should not be possible for passwords les than 6 characters");
    }

    @Test
    public void login_of_User_NoOK() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWrongLogin),
                "Registration should not be possible for login les than 6 characters");
    }

    @Test
    public void correct_user_registration_Success() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        assertEquals(validUser, registrationService.register(validUser),
                "In case of successfulregistration methor should return registered user object");
    }
}
