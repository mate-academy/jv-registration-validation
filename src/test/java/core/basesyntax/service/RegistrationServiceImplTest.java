package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final User userNull = null;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void registerAndGetNewUser_Ok() {
        User userFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        User userSecond = createUser(70, "freedom", "Bayraktar");
        registrationService.register(userFirst);
        User expectedUser1 = storageDao.get(Storage.people.get(0).getLogin());
        assertEquals(userFirst, expectedUser1);
        registrationService.register(userSecond);
        User expectedUser2 = storageDao.get(Storage.people.get(1).getLogin());
        assertEquals(userSecond, expectedUser2);
    }

    @Test
    void registerAndGetDifferentUsers_Ok() {
        User userFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        User userSecond = createUser(70, "freedom", "Bayraktar");
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User expectedUser1 = storageDao.get(Storage.people.get(0).getLogin());
        User expectedUser2 = storageDao.get(Storage.people.get(1).getLogin());
        assertNotEquals(expectedUser1, expectedUser2);
    }

    @Test
    void userNull_GetExceptionMessage_Ok() {
        try {
            registrationService.register(userNull);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userOneDouble_ExceptionMessage_Ok() {
        User userFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        User userSecond = createUser(70, "freedom", "Bayraktar");
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User userDoubleFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        try {
            registrationService.register(userDoubleFirst);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User should have unique login";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userTwoDouble_Exception_Ok() {
        User userFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        User userSecond = createUser(70, "freedom", "Bayraktar");
        registrationService.register(userFirst);
        registrationService.register(userSecond);
        User userDoubleSecond = createUser(70, "freedom", "Bayraktar");
        assertThrows(RuntimeException.class, () -> registrationService.register(userDoubleSecond));
    }

    @Test
    void userTooYoung_Exception_Ok() {
        User userTooYoung = createUser(10, "crimeabridgedestroyed", "Himars");
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooYoung));
    }

    @Test
    void userTooYoung_ExceptionMessage_Ok() {
        User userTooYoung = createUser(10, "crimeabridgedestroyed", "Himars");
        try {
            registrationService.register(userTooYoung);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User too young!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userTooOld_Exception_Ok() {
        User userTooOld = createUser(100, "crimeabridgedestroyed", "Himars");
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooOld));
    }

    @Test
    void userTooOld_ExceptionMessage_Ok() {
        User userTooOld = createUser(100, "crimeabridgedestroyed", "Himars");
        try {
            registrationService.register(userTooOld);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User too old!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userNegativeAge_ExceptionMessage_Ok() {
        User userNegativeAge = createUser(-100, "crimeabridgedestroyed", "Himars");
        try {
            registrationService.register(userNegativeAge);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User's age cannot be negative!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userNegativeAge_Exception_Ok() {
        User userNegativeAge = createUser(-100, "crimeabridgedestroyed", "Himars");
        assertThrows(RuntimeException.class, () -> registrationService.register(userNegativeAge));
    }

    @Test
    void userNullAge_Exception_Ok() {
        User userNullAge = createUser(null, "crimeabridgedestroyed", "Himars");
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullAge));
    }

    @Test
    void userNullAge_ExceptionMessage_Ok() {
        User userNullAge = createUser(null, "crimeabridgedestroyed", "Himars");
        try {
            registrationService.register(userNullAge);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User's age should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userNullPassword_Exception_Ok() {
        User userNullPassword = createUser(80, null, "Himars");
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void userNullPassword_ExceptionMessage_Ok() {
        User userNullPassword = createUser(80, null, "Himars");
        try {
            registrationService.register(userNullPassword);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Password should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userNullLogin_Exception_Ok() {
        User userNullLogin = createUser(80, "crimeabridgedestroyed", null);
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void userNullLogin_ExceptionMessage_Ok() {
        User userNullLogin = createUser(80, "crimeabridgedestroyed", null);
        try {
            registrationService.register(userNullLogin);
        } catch (NullPointerException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Login should not be null";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userWrongPassword_Exception_Ok() {
        User userWrongPassword = createUser(20, "cri", "HIMARS");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongPassword));
    }

    @Test
    void userWrongPassword_ExceptionMessage_Ok() {
        User userWrongPassword = createUser(20, "cri", "HIMARS");
        try {
            registrationService.register(userWrongPassword);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "Password too short!";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userEmptyLogin_Exception_Ok() {
        User userEmptyLogin = createUser(20, "crimeabridgedestroyed", "");
        assertThrows(RuntimeException.class, () -> registrationService.register(userEmptyLogin));
    }

    @Test
    void userEmptyLogin_ExceptionMessage_Ok() {
        User userEmptyLogin = createUser(20, "crimeabridgedestroyed", "");
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
        User userGoodFirst = createUser(22, "crimeabridgedestroyed", "Himars");
        User userGoodSecond = createUser(70, "freedom", "Bayraktar");
        User userGoodThird = createUser(30, "bavovna", "Javelin");
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

