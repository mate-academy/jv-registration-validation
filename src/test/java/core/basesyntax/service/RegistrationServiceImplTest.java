package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int VALID_AGE = 21;
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void testRegister_userWhoIsRegistered_notOk() {
        User user = new User();
        user.setAge(19);
        user.setLogin("login1234");
        user.setPassword("password1234");
        Storage.people.add(user);
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(user),
                "method need throw UserInvalidDataException for user that contains in storage");
    }

    @Test
    void testRegister_null_notOk() {
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(null),
                "method need to throw UserInvalidDataException for user: null");
    }

    @Test
    void testRegister_nullFields_not_Ok() {
        User userNullLogin = new User();
        userNullLogin.setPassword(VALID_PASSWORD);
        User userNullPassword = new User();
        userNullPassword.setLogin(VALID_LOGIN);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userNullLogin),
                "method need throw UserInvalidDataException for login: null");
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userNullPassword),
                "method need throw UserInvalidDataException for password: null");
    }

    @Test
    void testRegister_nonValidAge_notOk() {
        User userInvalidAge = new User();
        userInvalidAge.setLogin(VALID_LOGIN);
        userInvalidAge.setPassword(VALID_PASSWORD);
        userInvalidAge.setAge(10);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userInvalidAge),
                "method need throw UserInvalidDataException for age: "
                        + userInvalidAge.getAge());
    }

    @Test
    void testRegister_ok() {
        User validUser = new User();
        validUser.setAge(VALID_AGE);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setLogin(VALID_LOGIN);
        assertEquals(validUser, registrationService.register(validUser),
                "method need return user: " + validUser);
        User anotherValidUser = new User();
        anotherValidUser.setPassword(VALID_PASSWORD);
        anotherValidUser.setLogin("valid login number two");
        anotherValidUser.setAge(18);
        assertEquals(anotherValidUser, registrationService.register(anotherValidUser),
                "method need return user: " + anotherValidUser);
    }

    @Test
    void testRegister_nonValidLogin_notOk() {
        User nonValidUser = new User();
        nonValidUser.setPassword(VALID_PASSWORD);
        nonValidUser.setLogin("");
        nonValidUser.setAge(VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for login:"
                        + nonValidUser.getLogin());
        User anotherNonValidUser = new User();
        anotherNonValidUser.setPassword(VALID_PASSWORD);
        anotherNonValidUser.setLogin("short");
        anotherNonValidUser.setAge(VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(anotherNonValidUser),
                "method need throw UserInvalidDataException for login: "
                        + anotherNonValidUser.getLogin());

    }

    @Test
    void testRegister_nonValidPassword_notOk() {
        User nonValidUser = new User();
        nonValidUser.setPassword("");
        nonValidUser.setLogin(VALID_LOGIN);
        nonValidUser.setAge(VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for password: "
                        + nonValidUser.getPassword());
        User anotherNonValidUser = new User();
        anotherNonValidUser.setPassword("short");
        anotherNonValidUser.setLogin(VALID_LOGIN);
        anotherNonValidUser.setAge(VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(anotherNonValidUser),
                "method need throw UserInvalidDataException for password: "
                        + anotherNonValidUser.getPassword());
    }

    @Test
    void testRegister_ageNull_notOk() {
        User nonValidUser = new User();
        nonValidUser.setPassword(VALID_PASSWORD);
        nonValidUser.setAge(null);
        nonValidUser.setLogin(VALID_LOGIN);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for age: null");

    }

}
