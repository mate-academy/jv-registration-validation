package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void notEnoughAge_Exception_OK() {
        User user = new User("Loginnir", "Password", 5);
        assertThrows(RegistrationException.class, () -> {
            User actual = registrationService.register(user);
        },"That massage mean that age is correct");
    }

    @Test
    void smallPassword_Exception_OK() {
        User user = new User(null, "Pass", 19);
        assertThrows(RegistrationException.class,() -> {
            User actual = registrationService.register(user);
        },"That massage mean that password large is correct");
    }

    @Test
    void smallLogin_Exception_OK() {
        User user = new User("Log", "Passwordius", 35);
        assertThrows(RegistrationException.class,() -> {
            User actual = registrationService.register(user);
        },"That massage mean that Login large is correct");
    }

    @Test
    void userAlreadyExist_Exception_OK() {
        User user = new User("Loginius", "Passwordius", 35);
        registrationService.register(user);

        User userWithSameLogin = new User("Loginius", "Passw1231", 25);

        assertThrows(RegistrationException.class,() -> {
            registrationService.register(userWithSameLogin);
        },"That massage mean that we cant find same person in List and that is good");
    }
}
