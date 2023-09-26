package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void notEnoughAge_Error() {
        User user = new User("Login", "Password", 3);
        assertThrows(RegistrationError.class, () -> {
            User actual = registrationService.register(user);
        });
    }

    @Test
    void smallPassword_Error() {
        User user = new User(null, "Pass", 19);
        assertThrows(RegistrationError.class,() -> {
            User actual = registrationService.register(user);
        });
    }

    @Test
    void smallLogin_Error() {
        User user = new User("Log", "Passwordius", 35);
        assertThrows(RegistrationError.class,() -> {
            User actual = registrationService.register(user);
        });
    }

    @Test
    void userAlreadyExist_Error() {
        User user = new User("Loginius", "Passwordius", 35);
        registrationService.register(user);

        User userWithSameLogin = new User("Loginius", "Passw1231", 25);

        assertThrows(RegistrationError.class,() -> {
            registrationService.register(userWithSameLogin);
        });
    }
}
