package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static final int DEFAULT_AGE = 18;
    private static User validUser;
    private static User barelyOkLogin;
    private static User barelyOkPassword;
    private static User barelyOkAge;
    private static User nullUser;
    private static User userLoginNull;
    private static User userPasswordNull;
    private static User userAgeNull;
    private static User emptyLogin;
    private static User invalidLogin;
    private static User almostOkLogin;
    private static User emptyPassword;
    private static User invalidPassword;
    private static User almostOkPassword;
    private static User invalidAge;
    private static User tooYoungAge;

    @BeforeAll
    static void beforeAll() {
        validUser = new User("test_login", "pass4test", 45);
        barelyOkLogin = new User("abc123", "pass1test", 19);
        barelyOkPassword = new User("login_test", "abc321", 20);
        nullUser = null;
        userLoginNull = new User(null, "pass4test", 45);
        userPasswordNull = new User("test_login1", null, 45);
        userAgeNull = new User("test_login2", "pass4test", null);
        emptyLogin = invalidLogin = new User("", "pass4test", 45);
        invalidLogin = new User("abc", "pass4test", 45);
        almostOkLogin = new User("abc12", "pass4test", 45);
        emptyPassword = new User("test_login", "", 45);
        invalidPassword = new User("test_login3", "abc", 45);
        almostOkPassword = new User("test_login3", "wrong", 45);
        invalidAge = new User("test_login", "pass4test", -5);
        tooYoungAge = new User("test_login", "pass4test", 17);
        barelyOkAge = new User("test_age", "pass4age", DEFAULT_AGE);
    }

    @Test
    void register_nullData_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUser),
                "Expected: invalidDataException when User is null \n");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userLoginNull),
                "Expected: invalidDataException when User login is null \n");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userPasswordNull),
                "Expected: invalidDataException when User password is null \n");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userAgeNull),
                "Expected: invalidDataException when User age is null \n");
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyLogin),
                "User login can't be empty");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLogin),
                "User login must have at least 6 characters");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(almostOkLogin),
                "User login must have at least 6 characters");
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyPassword),
                "User password can't be empty");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPassword),
                "User password must have at least 6 characters");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(almostOkPassword),
                "User password must have at least 6 characters");
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAge),
                "User must be at least" + DEFAULT_AGE + "years old");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(tooYoungAge),
                "User must be at least" + DEFAULT_AGE + "years old");
    }

    @Test
    void register_validData_ok() {
        User tempUser = registrationService.register(validUser);
        assertEquals(validUser, tempUser, "Method register return wrong data");
        tempUser = registrationService.register(barelyOkLogin);
        assertEquals(barelyOkLogin, tempUser, "Method register return wrong data");
        tempUser = registrationService.register(barelyOkPassword);
        assertEquals(barelyOkPassword, tempUser, "Method register return wrong data");
        tempUser = registrationService.register(barelyOkAge);
        assertEquals(barelyOkAge, tempUser, "Method register return wrong data");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser),
                "User with this login is already registered!");
    }
}
