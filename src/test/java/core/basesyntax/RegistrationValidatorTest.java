package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class RegistrationValidatorTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final Object object = new Object();
    private static final StorageDao storageDao = new StorageDaoImpl();
    private User validUser;
    private User validUserCopy;
    private User userWithoutAtInLogin;
    private User userWithInvalidPass;
    private User userWithEmailNumberStart;
    private User userWithEmailSymbolStart;
    private User userWithUppercaseLettersInEmail;
    private User userWithInvalidAge;
    private User userWithNullLogin;
    private User userWithInvalidID;
    private User userNull;
    private User defaultUser;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        userWithoutAtInLogin = new User("logingmail.com", "1234567", 20);
        userWithInvalidPass = new User("login@gmail.com", "1234", 19);
        userWithEmailNumberStart = new User("232login@gmail.com", "1234567", 20);
        userWithEmailSymbolStart = new User("$$$$login@gmail.com", "1234567", 29);
        userWithUppercaseLettersInEmail = new User("LoGiN@gmail.com", "1234567", 23);
        userWithInvalidAge = new User("login123@gmail.com", "1234567", 15);
        validUser = new User("login321@gmail.com", "1234567", 22);
        userWithNullLogin = new User(null, "1234567", 23);
        userWithInvalidID = new User("login@gmail.com", "1234567", 45);
        validUserCopy = new User("login321@gmail.com", "1234567", 22);
        defaultUser = new User();

    }

    @Test
    void email_DoesNot_Contain_At_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithoutAtInLogin);
        });
    }

    @Test
    void password_Is_Less_Than_6_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithInvalidPass);
        });
    }

    @Test
    void start_With_Number_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithEmailNumberStart);
        });
    }

    @Test
    void start_With_Symbol_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithEmailSymbolStart);
        });
    }

    @Test
    void login_Contains_Uppercase_Letters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithUppercaseLettersInEmail);
        });
    }

    @Test
    void invalid_Age_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithInvalidAge);
        });
    }

    @Test
    void user_IsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userNull);
        });
    }

    @Test
    void login_IsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void two_Users_With_TheSameLogins_NotOk() {
        storageDao.add(new User("login321@gmail.com", "1234567", 31));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void storageDao_Get_And_Add_Valid_OK() {
        storageDao.add(new User("first123@gmail.com", "1234567", 22));
        storageDao.add(new User("second123@gmail.com", "1234567", 22));
        User firstUser = storageDao.get("first123@gmail.com");
        User secondUser = storageDao.get("second123@gmail.com");
        boolean expected = Objects.equals(firstUser.getAge(), secondUser.getAge());
        assertTrue(expected);
    }

    @Test
    void users_Are_Equal_Ok() {
        boolean expected = validUser.equals(validUserCopy);
        assertTrue(expected);
        expected = validUser.hashCode() == validUserCopy.hashCode();
        assertTrue(expected);
    }

    @Test
    void setters_Are_Correct_Ok() {
        defaultUser.setPassword("vavelon321");
        defaultUser.setLogin("sebastianmckinly1993@gmail.com");
        defaultUser.setAge(29);
        defaultUser.setId(1893452564L);
        boolean expected = defaultUser.equals(new User("sebastianmckinly1993@gmail.com",
                "vavelon321", 29));
        assertTrue(expected);
    }

    @Test
    void user_Equals_Object_NotOk() {
        boolean expected = validUser.equals(object);
        assertFalse(expected);
    }
}
