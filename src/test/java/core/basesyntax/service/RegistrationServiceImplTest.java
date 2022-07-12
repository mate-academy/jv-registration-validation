package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long ID = 2022;
    private static final int VALID_AGE = 23;
    private static final String VALID_PASSWORD = "password";
    private static final String FIRST_LOGIN = "FirstUser";
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SYMBOLS = 6;
    private static final String NEW_LOGIN = "SecondUser";
    private static final String INVALID_PASSWORD = "start";
    private static final int INVALID_AGE = 17;
    private static RegistrationService registerObject;

    @BeforeAll
    static void beforeAll() {
        registerObject = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User validUser = new User();
        validUser.setAge(VALID_AGE);
        validUser.setLogin(FIRST_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        assertEquals(validUser, registerObject.register(validUser),
                "We should register valid user");
    }

    @Test
    void register_userWithId_notOk() {
        User userWithId = new User();
        userWithId.setLogin(NEW_LOGIN);
        userWithId.setAge(VALID_AGE);
        userWithId.setPassword(VALID_PASSWORD);
        userWithId.setId(ID);
        assertThrows(RuntimeException.class,() -> registerObject.register(userWithId),
                "ID should set automatically");
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setAge(VALID_AGE);
        userWithInvalidLogin.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidLogin),
                "User cannot be with null login!");
        userWithInvalidLogin.setLogin("");
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidLogin),
                "User cannot have empty login!");
    }

    @Test
    void register_password_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setAge(VALID_AGE);
        userWithInvalidPassword.setLogin(NEW_LOGIN);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidPassword),
                "Cannot registers user with null password!");
        userWithInvalidPassword.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidPassword),
                "Password should contain at least " + MIN_PASSWORD_SYMBOLS + " symbols");
    }

    @Test
    void register_invalidAge_notOk() {
        User userWithInvalidAge = new User();
        userWithInvalidAge.setPassword(VALID_PASSWORD);
        userWithInvalidAge.setLogin(NEW_LOGIN);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "User should have age!");
        userWithInvalidAge.setAge(-1);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "Age cannot less or equals 0!");
        userWithInvalidAge.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "User cannot have less than " + MIN_AGE + " years old!");
    }

    @Test
    void register_theSameLogin_notOk() {
        User newValidUser = new User();
        newValidUser.setAge(VALID_AGE);
        newValidUser.setPassword(VALID_PASSWORD);
        newValidUser.setLogin(NEW_LOGIN);
        registerObject.register(newValidUser);
        assertThrows(RuntimeException.class, () -> registerObject.register(newValidUser),
                "User exists with login:" + newValidUser.getLogin());
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
