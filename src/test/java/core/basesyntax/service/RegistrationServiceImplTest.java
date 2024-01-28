package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static final int DEFAULT_AGE = 18;
    private static User user;
    private static User user2;
    private static User directlyOkLogin;
    private static User directlyOkPassword;
    private static User directlyOkAge;
    private static User nullUser;
    private static User userLoginNull;
    private static User userPasswordNull;
    private static User userAgeNull;
    private static User emptyLogin;
    private static User invalidLogin;
    private static User barelyOkLogin;
    private static User emptyPassword;
    private static User invalidPassword;
    private static User barelyOkPassword;
    private static User invalidAge;
    private static User tooYoungAge;

    @BeforeAll
    static void beforeAll() {
        user = new User("test_login", "pass4test", 45);
        user2 = new User("test_login2", "pass4test2", 22);
        directlyOkLogin = new User("abc123", "pass1test", 19);
        directlyOkPassword = new User("login_test", "abc321", 20);
        nullUser = null;
        userLoginNull = new User(null, "pass4test", 45);
        userPasswordNull = new User("test_login1", null, 45);
        userAgeNull = new User("test_login2", "pass4test", null);
        emptyLogin = invalidLogin = new User("", "pass4test", 45);
        invalidLogin = new User("abc", "pass4test", 45);
        barelyOkLogin = new User("abc12", "pass4test", 45);
        emptyPassword = new User("test_login", "", 45);
        invalidPassword = new User("test_login3", "abc", 45);
        barelyOkPassword = new User("test_login3", "wrong", 45);
        invalidAge = new User("test_login", "pass4test", -5);
        tooYoungAge = new User("test_login", "pass4test", 16);
        directlyOkAge = new User("test_age", "pass4age", DEFAULT_AGE);
    }

    @Test
    void checkNull() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(nullUser),
                "Expected: invalidDataException when User is null \n");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userLoginNull),
                "Expected: invalidDataException when User login is null \n");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userPasswordNull),
                "Expected: invalidDataException when User password is null \n");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userAgeNull),
                "Expected: invalidDataException when User age is null \n");
    }

    @Test
    void checkInvalidLogin() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(emptyLogin),
                "User login can't be empty");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidLogin),
                "User login must have at least 6 characters");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(barelyOkLogin),
                "User login must have at least 6 characters");
    }

    @Test
    void checkInvalidPassword() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(emptyPassword),
                "User password can't be empty");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidPassword),
                "User password must have at least 6 characters");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(barelyOkPassword),
                "User password must have at least 6 characters");
    }

    @Test
    void checkInvalidAge() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidAge),
                "User must be at least" + DEFAULT_AGE + "years old");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(tooYoungAge),
                "User must be at least" + DEFAULT_AGE + "years old");
    }

    @Test
    void validData() {
        User tempUser = registrationService.register(user);
        assertEquals(user, tempUser, "Method user return wrong data");
        tempUser = registrationService.register(directlyOkLogin);
        assertEquals(directlyOkLogin, tempUser, "Method user return wrong data");
        tempUser = registrationService.register(directlyOkPassword);
        assertEquals(directlyOkPassword, tempUser, "Method user return wrong data");
        tempUser = registrationService.register(directlyOkAge);
        assertEquals(directlyOkAge, tempUser, "Method user return wrong data");
        tempUser = registrationService.register(user);
        assertEquals(null, tempUser, "Method user return wrong data");
        tempUser = registrationService.register(user2);
        assertEquals(user2, tempUser, "Method user return wrong data");
    }
}
