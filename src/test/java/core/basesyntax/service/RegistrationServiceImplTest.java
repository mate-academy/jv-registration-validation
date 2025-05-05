package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "password";
    private static final String VALID_LOGIN = "admin admin";
    private static RegistrationService registrationService;

    @BeforeEach
    void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clear() {
        Storage.PEOPLE.clear();
    }

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
        User user = new User();
        user.setPassword(VALID_PASSWORD);
        user.setAge(17);
        user.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        user.setAge(-100);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
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
