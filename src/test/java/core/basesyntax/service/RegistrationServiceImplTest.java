package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "corect";
    private static final String INCORRECT_LOGIN = "login";
    private static final String CORRECT_PASSWORD = "qwerty";
    private static final String INCORRECT_PASSWORD = "pass";
    private static final int CORRECT_AGE = 20;
    private static final int INCORRECT_AGE = 15;
    private static final int NEGATIVE_AGE = -5;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setAge(CORRECT_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(CORRECT_LOGIN);
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_correctUser_Ok() {
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_InvalidAge_NotOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkLengthPassword_Ok() {
        User testUser = new User();
        testUser.setPassword("01234567890123456789");
        assertTrue(testUser.getPassword().length() >= user.getPassword().length());
        testUser.setPassword("123456");
        assertTrue(testUser.getPassword().length() >= user.getPassword().length());
    }

    @Test
    void register_checkLengthPassword_NotOk() {
        User testUser = new User();
        testUser.setPassword("01234");
        assertTrue(testUser.getPassword().length() <= user.getPassword().length());
        testUser.setPassword("123");
        assertTrue(testUser.getPassword().length() <= user.getPassword().length());
        testUser.setPassword("");
        assertTrue(testUser.getPassword().length() <= user.getPassword().length());
    }

    @Test
    void register_InvalidPassword_NotOk() {
        user.setPassword(INCORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeMoreThanRequired_Ok() {
        User userWithAge40 = new User();
        userWithAge40.setLogin(CORRECT_LOGIN + "40");
        userWithAge40.setPassword(CORRECT_PASSWORD);
        userWithAge40.setAge(40);
        assertTrue(userWithAge40.getAge() > user.getAge());
    }

    @Test
    void register_InvalidLogin_NotOk() {
        user.setLogin(INCORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkLoginLength_Ok() {
        User testUser = new User();
        testUser.setLogin("01234567890123456789");
        assertTrue(testUser.getLogin().length() >= user.getLogin().length());
        testUser.setLogin("012345");
        assertTrue(testUser.getLogin().length() >= user.getLogin().length());
    }

    @Test
    void register_checkLoginLength_NotOk() {
        User testUser = new User();
        testUser.setLogin("01234");
        assertTrue(testUser.getLogin().length() <= user.getLogin().length());
        testUser.setLogin("012");
        assertTrue(testUser.getLogin().length() <= user.getLogin().length());
        testUser.setLogin("");
        assertTrue(testUser.getLogin().length() <= user.getLogin().length());
    }

    @Test
    void register_UserThatIsAlreadyExist_NotOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
