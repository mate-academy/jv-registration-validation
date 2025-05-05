package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        User validUser = new User("login1234", "password1234", 19);
        Storage.people.add(validUser);
        assertTrue(Storage.people.contains(validUser));
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(new User("login1234", "password1234", 19)),
                "method need throw UserInvalidDataException for user that contains in storage");
    }

    @Test
    void testRegister_null_notOk() {
        assertThrows(UserInvalidDataException.class, () -> registrationService.register(null),
                "method need to throw UserInvalidDataException for user: null");
    }

    @Test
    void testRegister_nullLogin_notOk() {
        User userNullLogin = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userNullLogin),
                "method need throw UserInvalidDataException for login: null");
    }

    @Test
    void testRegister_nullPassword_notOk() {
        User userNullPassword = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userNullPassword),
                "method need throw UserInvalidDataException for password: null");
    }

    @Test
    void testRegister_nonValidAge_notOk() {
        User userInvalidAge = new User(VALID_LOGIN, VALID_PASSWORD, 10);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userInvalidAge),
                "method need throw UserInvalidDataException for age: "
                        + userInvalidAge.getAge());
    }

    @Test
    void testRegister_validUser_ok() {
        for (int i = 0; i < 100; i++) {
            User validUser = new User(VALID_LOGIN + i, VALID_PASSWORD + i, VALID_AGE + i);
            assertEquals(validUser, registrationService.register(validUser),
                    "method need return user: " + validUser);
            assertTrue(Storage.people.contains(validUser));
        }
    }

    @Test
    void testRegister_nonValidLogin_notOk() {
        User nonValidUser = new User("", VALID_PASSWORD, VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for login:"
                        + nonValidUser.getLogin());
        User anotherNonValidUser = new User("short", VALID_PASSWORD, VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(anotherNonValidUser),
                "method need throw UserInvalidDataException for login: "
                        + anotherNonValidUser.getLogin());

    }

    @Test
    void testRegister_nonValidPassword_notOk() {
        User nonValidUser = new User(VALID_LOGIN, "", VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for password: "
                        + nonValidUser.getPassword());
        User anotherNonValidUser = new User("valid login number two", "short", VALID_AGE);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(anotherNonValidUser),
                "method need throw UserInvalidDataException for password: "
                        + anotherNonValidUser.getPassword());
    }

    @Test
    void testRegister_ageNull_notOk() {
        User nonValidUser = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(nonValidUser),
                "method need throw UserInvalidDataException for age: null");

    }

}
