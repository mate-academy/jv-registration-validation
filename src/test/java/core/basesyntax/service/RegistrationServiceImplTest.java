package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User firstUser;
    private static User secondUser;
    private static User thirdUser;
    private static User user;
    private static Storage storage;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setAge(19);
        firstUser.setLogin("FirstUser");
        firstUser.setPassword("asdasd");
        secondUser = new User();
        secondUser.setAge(22);
        secondUser.setLogin("SecondUser");
        secondUser.setPassword("asdasdasd");
        thirdUser = new User();
        thirdUser.setAge(105);
        thirdUser.setLogin("ThirdUser");
        thirdUser.setPassword("jkasdkjl3jk8");
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Age is null!");

    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Login is null!");
    }

    @Test
    void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Password is null!");

    }

    @Test
    void register_passwordLess6Characters_notOk() {
        firstUser.setPassword("asdas");
        secondUser.setPassword("asd");
        thirdUser.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Password is less 6 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUser);
        }, "Test failed! RegistrationException should be thrown if Password is less 6 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(thirdUser);
        }, "Test failed! RegistrationException should be thrown if Password is less 6 character!");
    }

    @Test
    void register_addThreeValidUsers_ok() {
        firstUser.setLogin("asdasd");
        User expected = firstUser;
        User actual = registrationService.register(firstUser);
        assertEquals(expected, actual, "Return user must be equals added user");
        expected = secondUser;
        actual = registrationService.register(secondUser);
        assertEquals(expected, actual, "Return user must be equals added user");
        expected = thirdUser;
        actual = registrationService.register(thirdUser);
        assertEquals(expected, actual, "Return user must be equals added user");
    }

    @Test
    void register_loginLess6Characters_notOk() {
        firstUser.setLogin("asdas");
        secondUser.setLogin("asd");
        thirdUser.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Login is less 6 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUser);
        }, "Test failed! RegistrationException should be thrown if Login is less 6 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(thirdUser);
        }, "Test failed! RegistrationException should be thrown if Login is less 6 character!");
    }

    @Test
    void resister_ageLess18_notOk() {
        firstUser.setAge(17);
        secondUser.setAge(0);
        thirdUser.setAge(-19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if Age is less 18 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUser);
        }, "Test failed! RegistrationException should be thrown if Age is less 18 character!");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(thirdUser);
        }, "Test failed! RegistrationException should be thrown if Age is less 18 character!");
    }

    @Test
    void register_addUsersTheSameLogin_notOk() {
        secondUser.setLogin("FirstUser");
        registrationService.register(firstUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUser);
        }, "Test failed! RegistrationException should be thrown "
                + "if User with this login already exists in the Storage.");
    }

    @Test
    void register_nullUser_notOk() {
        firstUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(firstUser);
        }, "Test failed! RegistrationException should be thrown if User is null");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
