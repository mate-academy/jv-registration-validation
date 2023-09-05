package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_LOGIN = "admin admin";
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_passwordNull_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(VALID_AGE);
        userWithNullPassword.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullPassword));
    }

    @Test
    void register_loginNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword(VALID_PASSWORD);
        userWithNullLogin.setAge(VALID_AGE);
        userWithNullLogin.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_ageNull_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setPassword(VALID_PASSWORD);
        userWithNullAge.setAge(null);
        userWithNullAge.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNullAge));
    }

    @Test
    void register_invalidAge_notOk() {
        User userYoungerThen18 = new User();
        userYoungerThen18.setPassword(VALID_PASSWORD);
        userYoungerThen18.setAge(17);
        userYoungerThen18.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userYoungerThen18));
        userYoungerThen18.setAge(-100);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userYoungerThen18));
    }

    @Test
    void register_invalidPassword_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setAge(VALID_AGE);
        userWithInvalidPassword.setLogin(VALID_LOGIN);
        userWithInvalidPassword.setPassword("****");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setPassword(VALID_PASSWORD);
        userWithInvalidLogin.setAge(VALID_AGE);
        userWithInvalidLogin.setLogin("****");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void register_userExists_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        registrationService.register(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
