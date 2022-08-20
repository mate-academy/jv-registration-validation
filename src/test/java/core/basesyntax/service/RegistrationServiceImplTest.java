package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final User userNull = null;
    private static final String DEFAULT_LOGIN_FIRST = "Himars";
    private static final String DEFAULT_LOGIN_SECOND = "Bayraktar";
    private static final String DEFAULT_PASSWORD_FIRST = "crimeabridgedestroyed";
    private static final String DEFAULT_PASSWORD_SECOND = "bavovna";

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_GetNewUser_Ok() {
        User userFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        User userSecond = createUser(70, DEFAULT_PASSWORD_SECOND, DEFAULT_LOGIN_SECOND);
        registrationService.register(userFirst);
        User expectedUser1 = Storage.people.get(0);
        assertEquals(userFirst, expectedUser1);
        registrationService.register(userSecond);
        User expectedUser2 = Storage.people.get(1);
        assertEquals(userSecond, expectedUser2);
    }

    @Test
    void register_GetDifferentUsers_Ok() {
        User userFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        User userSecond = createUser(70, DEFAULT_PASSWORD_SECOND, DEFAULT_LOGIN_SECOND);
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User expectedUser1 = Storage.people.get(0);
        User expectedUser2 = Storage.people.get(1);
        assertNotEquals(expectedUser1, expectedUser2);
    }

    @Test
    void register_userNullGetExceptionMessage_Ok() {
        try {
            registrationService.register(userNull);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userOneDoubleExceptionMessage_Ok() {
        User userFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        registrationService.register(userFirst);
        User userDoubleFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userDoubleFirst);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User should have unique login";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userTwoDoubleException_notOk() {
        User userFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        User userSecond = createUser(70, DEFAULT_PASSWORD_SECOND, DEFAULT_LOGIN_SECOND);
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User userDoubleSecond = createUser(70, DEFAULT_PASSWORD_SECOND, DEFAULT_LOGIN_SECOND);
        assertThrows(RuntimeException.class, () -> registrationService.register(userDoubleSecond));
    }

    @Test
    void register_userTooYoungException_notOk() {
        User userTooYoung = createUser(10, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooYoung));
    }

    @Test
    void register_userTooYoungExceptionMessage_Ok() {
        User userTooYoung = createUser(10, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userTooYoung);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User too young!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userTooOldException_notOk() {
        User userTooOld = createUser(100, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooOld));
    }

    @Test
    void register_userTooOldExceptionMessage_Ok() {
        User userTooOld = createUser(100, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userTooOld);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User too old!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userNegativeAgeExceptionMessage_notOk() {
        User userNegativeAge = createUser(-100, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userNegativeAge);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User's age cannot be negative!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userNegativeAgeException_notOk() {
        User userNegativeAge = createUser(-100, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        assertThrows(RuntimeException.class, () -> registrationService.register(userNegativeAge));
    }

    @Test
    void register_userNullAgeException_notOk() {
        User userNullAge = createUser(null, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullAge));
    }

    @Test
    void register_userNullAgeExceptionMessage_Ok() {
        User userNullAge = createUser(null, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userNullAge);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User's age should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userNullPasswordException_notOk() {
        User userNullPassword = createUser(80, null, DEFAULT_LOGIN_FIRST);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_userNullPasswordExceptionMessage_Ok() {
        User userNullPassword = createUser(80, null, DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userNullPassword);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Password should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userNullLoginException_notOk() {
        User userNullLogin = createUser(80, DEFAULT_PASSWORD_FIRST, null);
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_userNullLoginExceptionMessage_Ok() {
        User userNullLogin = createUser(80, DEFAULT_PASSWORD_FIRST, null);
        try {
            registrationService.register(userNullLogin);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Login should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userWrongPasswordException_notOk() {
        User userWrongPassword = createUser(20, "cri", DEFAULT_LOGIN_FIRST);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongPassword));
    }

    @Test
    void register_userWrongPasswordExceptionMessage_Ok() {
        User userWrongPassword = createUser(20, "cri", DEFAULT_LOGIN_FIRST);
        try {
            registrationService.register(userWrongPassword);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Password too short!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_userEmptyLoginException_notOk() {
        User userEmptyLogin = createUser(20, DEFAULT_PASSWORD_FIRST, "");
        assertThrows(RuntimeException.class, () -> registrationService.register(userEmptyLogin));
    }

    @Test
    void register_userEmptyLoginExceptionMessage_Ok() {
        User userEmptyLogin = createUser(20, DEFAULT_PASSWORD_FIRST, "");
        try {
            registrationService.register(userEmptyLogin);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Login should not be blank!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void register_GetBaseSize_Ok() {
        User userGoodFirst = createUser(22, DEFAULT_PASSWORD_FIRST, DEFAULT_LOGIN_FIRST);
        User userGoodSecond = createUser(70, DEFAULT_PASSWORD_SECOND, DEFAULT_LOGIN_SECOND);
        User userGoodThird = createUser(30, "counterattack", "Javelin");
        registrationService.register(userGoodFirst);
        registrationService.register(userGoodSecond);
        registrationService.register(userGoodThird);
        int actual = Storage.people.size();
        assertEquals(3, actual);
    }

    private User createUser(Integer age, String password, String login) {
        User user = new User();
        user.setAge(age);
        user.setPassword(password);
        user.setLogin(login);
        return user;
    }
}

