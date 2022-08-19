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
    private static final User userGood1 = new User();
    private static final User userGood2 = new User();
    private static final User userGood3 = new User();
    private static final User userDouble1 = new User();
    private static final User userDouble2 = new User();
    private static final User userNull = null;
    private static final User userTooYoung = new User();
    private static final User userTooOld = new User();
    private static final User userNegativeAge = new User();
    private static final User userNullAge = new User();
    private static final User userNullLogin = new User();
    private static final User userNullPassword = new User();
    private static final User userWrongPassword = new User();
    private static final User userEmptyLogin = new User();

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
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        registrationService.register(userGood1);
        User expectedUser1 = storageDao.get(userGood1.getLogin());
        registrationService.register(userGood2);
        assertEquals(userGood1, expectedUser1);
        User expectedUser2 = storageDao.get(userGood2.getLogin());
        assertEquals(userGood2, expectedUser2);
    }

    @Test
    void registerAndGetDifferentUsers_Ok() {
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        registrationService.register(userGood1);
        User expectedUser1 = storageDao.get(userGood1.getLogin());
        User expectedUser2 = storageDao.get(userGood2.getLogin());
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
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        registrationService.register(userGood1);
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        registrationService.register(userGood2);
        userDouble1.setAge(22);
        userDouble1.setPassword("crimeabridgedestroyed");
        userDouble1.setLogin("Himars");
        userDouble2.setAge(70);
        userDouble2.setPassword("freedom");
        userDouble2.setLogin("Bayraktar");
        try {
            registrationService.register(userDouble1);
        } catch (RuntimeException e) {
            String actualMessage = e.getMessage();
            String expectedMessage = "User should have unique login";
            assertEquals(actualMessage, expectedMessage);
        }
    }

    @Test
    void userTwoDouble_Exception_Ok() {
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        registrationService.register(userGood1);
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        registrationService.register(userGood2);
        userGood3.setAge(56);
        userGood3.setPassword("moscowdrown");
        userGood3.setLogin("Neptune");
        registrationService.register(userGood3);
        userDouble2.setAge(70);
        userDouble2.setPassword("freedom");
        userDouble2.setLogin("Bayraktar");
        assertThrows(RuntimeException.class, () -> registrationService.register(userDouble2));
    }

    @Test
    void userTooYoung_Exception_Ok() {
        userTooYoung.setAge(10);
        userTooYoung.setPassword("bullet");
        userTooYoung.setLogin("MLRS");
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooYoung));
    }

    @Test
    void userTooYoung_ExceptionMessage_Ok() {
        userTooYoung.setAge(10);
        userTooYoung.setPassword("bullet");
        userTooYoung.setLogin("MLRS");
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
        userTooOld.setAge(100);
        userTooOld.setPassword("machinegewehr");
        userTooOld.setLogin("Atacms");
        assertThrows(RuntimeException.class, () -> registrationService.register(userTooOld));
    }

    @Test
    void userTooOld_ExceptionMessage_Ok() {
        userTooOld.setAge(100);
        userTooOld.setPassword("machinegewehr");
        userTooOld.setLogin("Atacms");
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
        userNegativeAge.setAge(-100);
        userNegativeAge.setPassword("sturmgewehr");
        userNegativeAge.setLogin("mig29");
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
        userNegativeAge.setAge(-100);
        userNegativeAge.setPassword("sturmgewehr");
        userNegativeAge.setLogin("mig29");
        assertThrows(RuntimeException.class, () -> registrationService.register(userNegativeAge));
    }

    @Test
    void userNullAge_Exception_Ok() {
        userNullAge.setAge(null);
        userNullAge.setPassword("kalash");
        userNullAge.setLogin("Nasams");
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullAge));
    }

    @Test
    void userNullAge_ExceptionMessage_Ok() {
        userNullAge.setAge(null);
        userNullAge.setPassword("kalash");
        userNullAge.setLogin("Nasams");
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
        userNullPassword.setAge(19);
        userNullPassword.setPassword(null);
        userNullPassword.setLogin("Harpoon");
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void userNullPassword_ExceptionMessage_Ok() {
        userNullPassword.setAge(19);
        userNullPassword.setPassword(null);
        userNullPassword.setLogin("Harpoon");
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
        userNullLogin.setAge(33);
        userNullLogin.setPassword("bavovna");
        userNullLogin.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void userNullLogin_ExceptionMessage_Ok() {
        userNullLogin.setAge(33);
        userNullLogin.setPassword("bavovna");
        userNullLogin.setLogin(null);
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
        userWrongPassword.setAge(46);
        userWrongPassword.setPassword("mlrs");
        userWrongPassword.setLogin("Javelin");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWrongPassword));
    }

    @Test
    void userWrongPassword_ExceptionMessage_Ok() {
        userWrongPassword.setAge(46);
        userWrongPassword.setPassword("mlrs");
        userWrongPassword.setLogin("Javelin");
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
        userEmptyLogin.setAge(32);
        userEmptyLogin.setPassword("revenge");
        userEmptyLogin.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(userEmptyLogin));
    }

    @Test
    void userEmptyLogin_ExceptionMessage_Ok() {
        userEmptyLogin.setAge(32);
        userEmptyLogin.setPassword("revenge");
        userEmptyLogin.setLogin("");
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
        userGood1.setAge(22);
        userGood1.setPassword("crimeabridgedestroyed");
        userGood1.setLogin("Himars");
        userGood2.setAge(70);
        userGood2.setPassword("freedom");
        userGood2.setLogin("Bayraktar");
        userGood3.setAge(56);
        userGood3.setPassword("moscowdrown");
        userGood3.setLogin("Neptune");
        registrationService.register(userGood1);
        registrationService.register(userGood2);
        registrationService.register(userGood3);
        int actual = Storage.people.size();
        assertEquals(3, actual);
    }
}
