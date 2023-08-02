package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String CORRECT_LOGIN = "1234567";
    private static final String INCORRECT_LOGIN = "123";
    private static final String BORDER_LOGIN = "123456";
    private static final String EMPTY_LINE = "";
    private static final String CORRECT_PASSWORD = "1234567";
    private static final String INCORRECT_PASSWORD = "123";
    private static final String BORDER_PASSWORD = "123456";
    private static final Long CORRECT_ID = 1L;
    private static final int CORRECT_AGE = 22;
    private static final int IN_CORRECT_AGE = 15;
    private static final int BORDER_AGE = 18;
    private static final int NEG_AGE = -1;
    private static RegistrationService registrationService;
    private final User testLogin = new User(CORRECT_ID, EMPTY_LINE, CORRECT_PASSWORD, BORDER_AGE);
    private final User testPassword = new User(CORRECT_ID, CORRECT_LOGIN, EMPTY_LINE, BORDER_AGE);
    private final User testAge = new User(CORRECT_ID, CORRECT_LOGIN, CORRECT_PASSWORD, 0);
    private final User tesId = new User(null, CORRECT_LOGIN, CORRECT_PASSWORD, BORDER_AGE);
    private final User testUserOne = new User(CORRECT_ID, CORRECT_LOGIN,
            CORRECT_PASSWORD, BORDER_AGE);
    private final User testUserSecondOne = new User(CORRECT_ID, CORRECT_LOGIN,
            CORRECT_PASSWORD, BORDER_AGE);
    private final User testUserThreeNew = new User(CORRECT_ID,"NEW USER 3",
            CORRECT_PASSWORD, BORDER_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void loginIsGreaterThanMinimum() throws RegistrationException {
        String login = CORRECT_LOGIN;
        testLogin.setLogin(login);
        registrationService.register(testLogin);
        Assertions.assertEquals(login,testLogin.getLogin());
    }

    @Test
    void loginIsLessThanMinimum() {
        testLogin.setLogin(INCORRECT_LOGIN);
        try {
            registrationService.register(testLogin);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown is login  is incorrect");
    }

    @Test
    void loginEmpty() {
        testLogin.setLogin(EMPTY_LINE);
        try {
            registrationService.register(testLogin);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown - login is empty line");
    }

    @Test
    void loginIsMinimum() throws RegistrationException {
        String login = BORDER_LOGIN;
        testLogin.setLogin(login);
        registrationService.register(testLogin);
        Assertions.assertEquals(login,testLogin.getLogin());
    }

    @Test
    void registerNullLogin_notOk() {
        testLogin.setLogin(null);
        try {
            registrationService.register(testLogin);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown - login is null");
    }

    @Test
    void passwordIsGreaterThanMinimum() throws RegistrationException {
        String password = CORRECT_PASSWORD;
        testPassword.setPassword(password);
        registrationService.register(testPassword);
        Assertions.assertEquals(password,testPassword.getPassword());
    }

    @Test
    void passwordIsLessThanMinimum() {
        testPassword.setPassword(INCORRECT_PASSWORD);
        try {
            registrationService.register(testPassword);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown - password is incorrect");
    }

    @Test
    void passwordEmpty() {
        testPassword.setPassword(EMPTY_LINE);
        try {
            registrationService.register(testPassword);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown - password is empty line");
    }

    @Test
    void passwordIsMinimum() throws RegistrationException {
        String password = BORDER_PASSWORD;
        testPassword.setPassword(password);
        registrationService.register(testPassword);
        Assertions.assertEquals(password,testPassword.getPassword());
    }

    @Test
    void registerNullPassword_notOk() {
        testPassword.setPassword(null);
        try {
            registrationService.register(testPassword);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown is password is null");
    }

    @Test
    void register_nullAge_notOk() {
        try {
            registrationService.register(testAge);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown if age is null");
    }

    @Test
    void registerAgeLessThen_Null() {
        testAge.setAge(NEG_AGE);
        try {
            registrationService.register(testAge);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown if age is negative");
    }

    @Test
    void ageIsBorder() throws RegistrationException {
        int age = BORDER_AGE;
        testAge.setAge(age);
        registrationService.register(testAge);
        Assertions.assertEquals(age,testAge.getAge());
    }

    @Test
    void ageIsAllowable() throws RegistrationException {
        int age = CORRECT_AGE;
        testAge.setAge(age);
        registrationService.register(testAge);
        Assertions.assertEquals(age,testAge.getAge());
    }

    @Test
    void ageIsNotAllowable() {
        testAge.setAge(IN_CORRECT_AGE);
        try {
            registrationService.register(testAge);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown if age is lesser then 18");
    }

    @Test
    void register_nullId_notOk() {
        try {
            registrationService.register(tesId);
        } catch (RegistrationException e) {
            return;
        }
        Assertions.fail("RegistrationException should be thrown is Id is null");
    }

    @Test
    void userIsAllowable() throws RegistrationException {
        Assertions.assertEquals(testUserOne,registrationService.register(testUserOne));
    }

    @Test
    void registrationPassed_Ok() throws RegistrationException {
        Storage.people.clear();
        Storage.people.add(testUserOne);
        User user = registrationService.register(testUserThreeNew);
        Assertions.assertEquals(2,Storage.people.size());
        Assertions.assertEquals(testUserThreeNew,user);
        Storage.people.clear();
    }

    @Test
    void userAlreadyRegistered_Ok() throws RegistrationException {
        Storage.people.clear();
        Storage.people.add(testUserOne);
        User user = registrationService.register(testUserOne);
        registrationService.register(testUserSecondOne);
        Assertions.assertEquals(1,Storage.people.size());
        Assertions.assertEquals(testUserOne,user);
    }
}
