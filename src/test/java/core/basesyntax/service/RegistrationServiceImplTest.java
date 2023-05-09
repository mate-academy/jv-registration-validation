package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AuthenticationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 20;
    private static final int INCORRECT_AGE = 17;
    private static final long DEFAULT_ID = 1L;
    private static final String DEFAULT_LOGIN = "johny_cash@gmail.com";
    private static final String DEFAULT_PASSWORD = "folsom";
    private static final String INCORRECT_PASSWORD_LOGIN = "ring";
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(null),
                "User should not be null");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "User age should not be null");
    }

    @Test
    void register_nullId_notOk() {
        user.setId(null);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "User id should not be null");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "User login should not be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "User password should not be null");
    }

    @Test
    void register_incorrectPassword_notOk() {
        user.setPassword(INCORRECT_PASSWORD_LOGIN);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "Password must not be shorter than 6 characters");
    }

    @Test
    void register_incorrectLogin_notOk() {
        user.setLogin(INCORRECT_PASSWORD_LOGIN);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "Login must not be shorter than 6 characters");
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "Age of the user must be at least 18");
    }

    @Test
    void register_loginAlreadyUse_notOk() {
        Storage.people.add(user);
        assertThrows(AuthenticationException.class, () ->
                registrationService.register(user),
                "Login is already in use");
    }
}
