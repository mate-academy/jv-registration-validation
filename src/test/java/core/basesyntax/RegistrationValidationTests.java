package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class RegistrationValidationTests {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    public void clean_storage() {
        registrationService.clearStorage();
    }

    @Test
    public void register_correct_user_registration_Success() {
        User validUser = new User("someuser", "userpass", 56);
        assertEquals(validUser, registrationService.register(validUser),
                "In case of successful registration method should return "
                        + "registered user object");
    }

    @Test
    public void register_duplicate_user_registration_NotOk() {
        User validUser2 = new User("somelogin", "userpa", 25);
        User sameLoginAsValidUser2 = new User("somelogin", "userpass", 56);
        registrationService.register(validUser2);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(sameLoginAsValidUser2),
                "Registration method should not accept user with login name used "
                        + "by previously registered users");
    }

    @Test
    public void register_age_of_User_NotOK() {
        User userWrongAge = new User("someuser3", "user3password", 17);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(userWrongAge),
                "Registration of user with age under 18 should not be allowed");
    }

    @Test
    public void register_password_of_User_NotOK() {
        User userWrongPass = new User("someuser1", "us1pa", 25);
        User userNullPass = new User("someuser1", null, 25);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongPass),
                "Registration should not be possible for passwords les than 6 characters");
        assertThrows(RuntimeException.class, () -> registrationService.register(userNullPass),
                "Registration should not be null");
    }

    @Test
    public void register_login_of_User_NoOK() {
        User userWrongLogin = new User("user", "userpassword", 25);
        User userNullLogin = new User(null, "userpassword", 25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWrongLogin),
                "Registration should not be possible for login les than 6 characters");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userNullLogin),
                "Registration should not be null");
    }
}
