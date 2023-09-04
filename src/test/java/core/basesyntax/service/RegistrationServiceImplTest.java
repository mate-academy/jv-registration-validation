package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private final int validAge = 18;
    private final String validPassword = "password";
    private final String validLogin = "admin admin";

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_passwordNull_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(validAge);
        userWithNullPassword.setLogin(validLogin);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullPassword));
    }

    @Test
    void register_loginNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword(validPassword);
        userWithNullLogin.setAge(validAge);
        userWithNullLogin.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_ageNull_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setPassword(validPassword);
        userWithNullAge.setAge(null);
        userWithNullAge.setLogin(validLogin);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullAge));
    }

    @Test
    void register_invalidAge_notOk() {
        User userYoungerThen18 = new User();
        userYoungerThen18.setPassword(validPassword);
        userYoungerThen18.setAge(17);
        userYoungerThen18.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userYoungerThen18));
        userYoungerThen18.setAge(-100);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userYoungerThen18));
    }

    @Test
    void register_invalidPassword_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setAge(validAge);
        userWithInvalidPassword.setLogin(validLogin);
        userWithInvalidPassword.setPassword("*".repeat(4));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setPassword(validPassword);
        userWithInvalidLogin.setAge(validAge);
        userWithInvalidLogin.setLogin("*".repeat(4));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void register_userExists_notOk() {
        User user = new User();
        user.setLogin(validLogin);
        user.setAge(validAge);
        user.setPassword(validPassword);
        registrationService.register(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

}
