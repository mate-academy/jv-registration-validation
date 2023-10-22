package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.EmptyFieldException;
import core.basesyntax.exception.IllegalAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.LoginExistingException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String NULL_TEST_FAILURE_MESSAGE = "Null fields "
            + "should throw EmptyFieldException!";

    private static final String LOGIN_EXISTS_FAILURE_MESSAGE = "Login already exists in storage "
            + "should throw LoginExistingException!";
    private static final String LOGIN_TEST_FAILURE_MESSAGE = "Login less than 6 letters "
            + "should throw InvalidLoginException!";
    private static final String PASSWORD_TEST_FAILURE_MESSAGE = "Password less than 6 letters "
            + "should throw InvalidPasswordException!";
    private static final String AGE_TEST_FAILURE_MESSAGE = "Age less than 18 "
            + "or over 122 should throw IllegalAgeException!";
    private static final String USER_BOB_LOGIN_DEFAULT = "bobjordan";
    private static final String USER_ALICE_LOGIN_DEFAULT = "alicebaker";
    private static final String LOGIN_WORSE_CASE = "bob";
    private static final String USER_PASSWORD_DEFAULT = "password";
    private static final String PASSWORD_WORSE_CASE = "pass";
    private static final Integer USER_AGE_DEFAULT = 18;
    private static final Integer LESS_AGE_WORSE_CASE = 16;
    private static final Integer OVER_AGE_WORSE_CASE = 210;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User bob;
    private User alice;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void registerUser_correctInputData() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        alice = new User(USER_ALICE_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        registrationService.register(bob);
        registrationService.register(alice);
        User expectedFirstUser = storageDao.get(bob.getLogin());
        User expectedSecondUser = storageDao.get(alice.getLogin());
        assertEquals(expectedFirstUser, bob);
        assertEquals(expectedSecondUser, alice);
    }

    @Test
    public void registerDuplicateUser_throwsRuntimeException() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        alice = bob;
        try {
            registrationService.register(bob);
            registrationService.register(alice);
        } catch (LoginExistingException e) {
            return;
        }
        fail(LOGIN_EXISTS_FAILURE_MESSAGE);
    }

    @Test
    public void registerDuplicateUserLogin_throwsRuntimeException() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        alice = new User(USER_BOB_LOGIN_DEFAULT, PASSWORD_WORSE_CASE, OVER_AGE_WORSE_CASE);
        try {
            registrationService.register(bob);
            registrationService.register(alice);
        } catch (LoginExistingException e) {
            return;
        }
        fail(LOGIN_EXISTS_FAILURE_MESSAGE);
    }

    @Test
    public void registerInvalidUserLogin_throwsRuntimeException() {
        bob = new User(LOGIN_WORSE_CASE, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        try {
            registrationService.register(bob);
        } catch (InvalidLoginException e) {
            return;
        }
        fail(LOGIN_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void registerInvalidUserPass_throwsRuntimeException() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, PASSWORD_WORSE_CASE, USER_AGE_DEFAULT);
        try {
            registrationService.register(bob);
        } catch (InvalidPasswordException e) {
            return;
        }
        fail(PASSWORD_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void registerInvalidUserAge1_throwsRuntimeException() {
        alice = new User(USER_ALICE_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, LESS_AGE_WORSE_CASE);
        try {
            registrationService.register(alice);
        } catch (IllegalAgeException e) {
            return;
        }
        fail(AGE_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void registerInvalidUserAge2_throwsRuntimeException() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, OVER_AGE_WORSE_CASE);
        try {
            registrationService.register(bob);
        } catch (IllegalAgeException e) {
            return;
        }
        fail(AGE_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void nullUserLogin_throwsRuntimeException() {
        bob = new User(null, USER_PASSWORD_DEFAULT, USER_AGE_DEFAULT);
        try {
            registrationService.register(bob);
        } catch (EmptyFieldException e) {
            return;
        }
        fail(NULL_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void nullUserPassword_throwsRuntimeException() {
        alice = new User(USER_ALICE_LOGIN_DEFAULT, null, USER_AGE_DEFAULT);
        try {
            registrationService.register(alice);
        } catch (EmptyFieldException e) {
            return;
        }
        fail(NULL_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void nullUserAge_throwsRuntimeException() {
        bob = new User(USER_BOB_LOGIN_DEFAULT, USER_PASSWORD_DEFAULT, null);
        try {
            registrationService.register(bob);
        } catch (EmptyFieldException e) {
            return;
        }
        fail(NULL_TEST_FAILURE_MESSAGE);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }
}
