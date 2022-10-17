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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationValidatorTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final Object object = new Object();
    private StorageDao storageDao = new StorageDaoImpl();
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
        userWithoutAtInLogin = new User(5685426986L, "logingmail.com", "1234567", 20);
        userWithInvalidPass = new User(9562423586L, "login@gmail.com", "1234", 19);
        userWithEmailNumberStart = new User(9562358526L, "232login@gmail.com", "1234567", 20);
        userWithEmailSymbolStart = new User(3201256852L, "$$$$login@gmail.com", "1234567", 29);
        userWithUppercaseLettersInEmail = new User(1258965478L, "LoGiN@gmail.com", "1234567", 23);
        userWithInvalidAge = new User(4562856235L, "login123@gmail.com", "1234567", 15);
        validUser = new User(5423159876L, "login321@gmail.com", "1234567", 22);
        userWithNullLogin = new User(123214856L, null, "1234567", 23);
        userWithInvalidID = new User(1359L, "login@gmail.com", "1234567", 45);
        validUserCopy = new User(5423159876L, "login321@gmail.com", "1234567", 22);
        defaultUser = new User();

    }

    @AfterEach
    void tearDown() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void emailDoesNotContainAt_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithoutAtInLogin);
        });
    }

    @Test
    void passwordIsLessThan6_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithInvalidPass);
        });
    }

    @Test
    void startWithNumber_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithEmailNumberStart);
        });
    }

    @Test
    void startWithSymbol_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithEmailSymbolStart);
        });
    }

    @Test
    void loginContainsUppercaseLetters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithUppercaseLettersInEmail);
        });
    }

    @Test
    void invalidAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithInvalidAge);
        });
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userNull);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void twoUsersWithTheSameLogins_NotOk() {
        storageDao.add(new User(4589562358L, "login321@gmail.com", "1234567", 31));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void invalidID_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithInvalidID);
        });
    }

    @Test
    void storageDaoGetAndAddAreValid_OK() {
        storageDao.add(new User(1256489562L, "first123@gmail.com", "1234567", 22));
        storageDao.add(new User(4587956214L, "second123@gmail.com", "1234567", 22));
        User firstUser = storageDao.get("first123@gmail.com");
        User secondUser = storageDao.get("second123@gmail.com");
        boolean expected = Objects.equals(firstUser.getAge(), secondUser.getAge());
        assertTrue(expected);
    }

    @Test
    void usersAreEqual_Ok() {
        boolean expected = validUser.equals(validUserCopy);
        assertTrue(expected);
        expected = validUser.hashCode() == validUserCopy.hashCode();
        assertTrue(expected);
    }

    @Test
    void settersAreCorrect_Ok() {
        defaultUser.setPassword("vavelon321");
        defaultUser.setLogin("sebastianmckinly1993@gmail.com");
        defaultUser.setAge(29);
        defaultUser.setId(1893452564L);
        boolean expected = defaultUser.equals(new User(1893452564L,"sebastianmckinly1993@gmail.com",
                "vavelon321", 29));
        assertTrue(expected);
    }

    @Test
    void userEqualsObject_NotOk() {
        boolean expected = validUser.equals(object);
        assertFalse(expected);
    }
}
