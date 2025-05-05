package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String CORRECT_LOGIN = "1234567";
    private static final String CORRECT_LOGIN_NEW = "7654321";
    private static final String INCORRECT_LOGIN = "123";
    private static final String BORDER_LOGIN = "123456";
    private static final String EMPTY_LINE = "";
    private static final String CORRECT_PASSWORD = "1234567";
    private static final String INCORRECT_PASSWORD = "123";
    private static final String BORDER_PASSWORD = "123456";
    private static final int CORRECT_AGE = 22;
    private static final int INCORRECT_AGE = 15;
    private static final int BORDER_AGE = 18;
    private static final int NEG_AGE = - 1;
    private static final User testUser = new User();
    private static final User testUserNew = new User();
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        testUserNew.setLogin(CORRECT_LOGIN_NEW);
        testUserNew.setPassword(CORRECT_PASSWORD);
        testUserNew.setAge(CORRECT_AGE);
    }

    @BeforeEach
    void setUp() {
        testUser.setAge(CORRECT_AGE);
        testUser.setPassword(CORRECT_PASSWORD);
        testUser.setLogin(CORRECT_LOGIN);
        Storage.people.clear();
    }

    @Test
    void register_loginValid_ok() {
        String login = CORRECT_LOGIN;
        testUser.setLogin(login);
        registrationService.register(testUser);
        Assertions.assertEquals(login, testUser.getLogin());
    }

    @Test
    void register_loginTooShort_notOk() {
        testUser.setLogin(INCORRECT_LOGIN);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin(EMPTY_LINE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_minimumShortLogin_ok() {
        testUser.setLogin(BORDER_LOGIN);
        registrationService.register(testUser);
        Assertions.assertEquals(BORDER_LOGIN, testUser.getLogin());
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordValid_ok() {
        String password = CORRECT_PASSWORD;
        testUser.setPassword(password);
        registrationService.register(testUser);
        Assertions.assertEquals(password, testUser.getPassword());
    }

    @Test
    void register_passwordTooShor_notOk() {
        testUser.setPassword(INCORRECT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        testUser.setPassword(EMPTY_LINE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_minimumShortPassword_ok() {
        String password = BORDER_PASSWORD;
        testUser.setPassword(password);
        registrationService.register(testUser);
        Assertions.assertEquals(password, testUser.getPassword());
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(0);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(NEG_AGE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_borderAge_ok() {
        int age = BORDER_AGE;
        testUser.setAge(age);
        registrationService.register(testUser);
        Assertions.assertEquals(age, testUser.getAge());
    }

    @Test
    void register_allowableAge_ok() {
        int age = CORRECT_AGE;
        testUser.setAge(age);
        registrationService.register(testUser);
        Assertions.assertEquals(age, testUser.getAge());
    }

    @Test
    void register_unlawfulAge_notOk() {
        testUser.setAge(INCORRECT_AGE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_allowableUser_ok() {
        Assertions.assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_validUser_ok_ok() {
        Storage.people.add(testUser);
        User user = registrationService.register(testUserNew);
        Assertions.assertEquals(2, Storage.people.size());
        Assertions.assertEquals(testUserNew, user);
    }

    @Test
    void register_registrationExistingUser_notOk() {
        Storage.people.add(testUser);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(testUser));
        Assertions.assertEquals(1, Storage.people.size());
    }
}
