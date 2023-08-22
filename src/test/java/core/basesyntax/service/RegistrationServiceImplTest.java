package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceImplTest {
    private static User validUser;
    private User loginUserTest;
    private User passwordUserTest;
    private User ageUserTest;
    private RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        validUser = new User();
        validUser.setLogin("login1");
        validUser.setId(123456789L);
        validUser.setAge(18);
        validUser.setPassword("123456");
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();

        loginUserTest = new User();

        passwordUserTest = new User();
        passwordUserTest.setLogin("login1");

        ageUserTest = new User();
        ageUserTest.setLogin("login1");
        ageUserTest.setPassword("123456");
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOK() {
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(loginUserTest));
    }

    @Test
    void register_emptyLogin_notOK() {
        loginUserTest.setLogin("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(loginUserTest));
    }

    @Test
    void register_3lengthLogin_notOk() {
        loginUserTest.setLogin("abc");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(loginUserTest));
    }

    @Test
    void register_5LengthLogin_notOk() {
        loginUserTest.setLogin("abcde");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(loginUserTest));
    }

    @Test
    void register_6LengthLogin_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_8LengthLogin_Ok() {
        validUser.setLogin("login123");
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_loginAlreadyExists_NotOk() {
        Storage.people.add(validUser);
        loginUserTest.setLogin("login1");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(loginUserTest));
        Storage.people.remove(validUser);
    }

    @Test
    void register_nullPassword_NotOk() {
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(passwordUserTest));
    }

    @Test
    void register_emptyPassword_NotOk() {
        passwordUserTest.setPassword("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(passwordUserTest));
    }

    @Test
    void register_3LengthPassword_NotOk() {
        passwordUserTest.setPassword("123");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(passwordUserTest));
    }

    @Test
    void register_5LengthPassword_NotOk() {
        passwordUserTest.setPassword("12345");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(passwordUserTest));
    }

    @Test
    void register_6LengthPassword_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_8LengthPassword_Ok() {
        validUser.setPassword("12345678");
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_nullAge_NotOk() {
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(ageUserTest));
    }

    @Test
    void register_negativeAge_NotOk() {
        ageUserTest.setAge(-10);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(ageUserTest));
    }

    @Test
    void register_zeroAge_NotOk() {
        ageUserTest.setAge(0);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(ageUserTest));
    }

    @Test
    void register_17Age_NotOk() {
        ageUserTest.setAge(17);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(ageUserTest));
    }

    @Test
    void register_18Age_Ok() {
        ageUserTest.setAge(18);
        User actual = registrationService.register(ageUserTest);
        assertEquals(ageUserTest, actual);
    }

    @Test
    void register_30Age_Ok() {
        ageUserTest.setAge(30);
        User actual = registrationService.register(ageUserTest);
        assertEquals(ageUserTest, actual);
    }
}
