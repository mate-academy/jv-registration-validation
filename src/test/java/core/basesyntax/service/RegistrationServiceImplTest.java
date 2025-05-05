package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "valid_login";
    private static final String DEFAULT_PASSWORD = "valid_password";
    private static final int DEFAULT_AGE = 25;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void testUserCreation() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
    }

    @Test
    void register_userAgeLowerThenValid_notOk() {
        user.setAge(15);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "You should be older than 18 to register");
    }

    @Test
    void register_userPasswordShorterThenExpected_notOk() {
        user.setPassword("hello");
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "Your password must contain more than 5 symbols");
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "Your password can't be empty");
    }

    @Test
    void register_userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "login can't be null");
    }

    @Test
    void register_userWithEmptyLogin_notOk() {
        user.setLogin("");
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "Your login can't be empty");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "password can't be null");
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        registrationService.register(user);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "Such user already registered");
    }

    @Test
    void register_validUser_ok() {
        assertEquals(user, registrationService.register(user),
                "User registered");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-19);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "Your age should be positive");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () ->
                registrationService.register(null),
                "User is null");
    }

    @Test
    void register_userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user),
                "User age can't be null");
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
