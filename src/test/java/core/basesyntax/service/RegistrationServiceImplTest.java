package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final long USER_ID = 1L;
    private static final String USER_LOGIN = "user_login";
    private static final String USER_PASSWORD = "user_password";
    private static final int USER_AGE = 122;
    private static final String NEW_USER_PASSWORD = ",fhfv,jkmrf";
    private static final int NEW_USER_AGE = 32;
    private static final int INVALID_AGE = 17;
    private static final String INVALID_PASSWORD = "000";
    private static final int INVALID_OLDER_AGE = 135;
    private static final String EMPTY_LOGIN = "";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registration_user_Ok() {
        user = createUser(USER_LOGIN, USER_PASSWORD, USER_AGE);
        User actualUser = registrationService.register(user);
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    void register_nullUser_notOk() {
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(null),
                "UserRegistrationException expected for user = null");
        assertEquals("User is null", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user = createUser(null, USER_PASSWORD, USER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for user.login = null");
        assertEquals("User's login is null", exception.getMessage());
    }

    @Test
    void register_emptyLogin_notOk() {
        user = createUser(EMPTY_LOGIN, USER_PASSWORD, USER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for empty user.login");
        assertEquals("User's login is empty", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user = createUser(USER_LOGIN, null, USER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for user.password = null");
        assertEquals("User's password is null", exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        user = createUser(USER_LOGIN, USER_PASSWORD, null);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for user.age = null");
        assertEquals("User's age is null", exception.getMessage());
    }

    @Test
    void register_largeAge_notOk() {
        user = createUser(USER_LOGIN, USER_PASSWORD, INVALID_OLDER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for large user.age");
        assertEquals("User's age is too big", exception.getMessage());
    }

    @Test
    void register_userAlreadyExist_notOk() {
        user = createUser(USER_LOGIN, USER_PASSWORD, USER_AGE);
        Storage.people.add(user);
        User newUser = createUser(USER_LOGIN, NEW_USER_PASSWORD, NEW_USER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(newUser),
                "UserRegistrationException expected for already stored user");
        assertEquals("User with such login already exists", exception.getMessage());
    }

    @Test
    void register_invalidAge_notOK() {
        user = createUser(USER_LOGIN, USER_PASSWORD, INVALID_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for invalid user's age");
        assertEquals("User's age should be at least 18", exception.getMessage());
    }

    @Test
    void register_invalidPassword_notOk() {
        user = createUser(USER_LOGIN, INVALID_PASSWORD, USER_AGE);
        var exception = assertThrows(UserRegistrationException.class, () ->
                        registrationService.register(user),
                "UserRegistrationException expected for invalid user's password");
        assertEquals("User's password should contain "
                + "at least 6 characters", exception.getMessage());
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
