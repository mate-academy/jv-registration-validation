package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.registration_exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String BOB_LOGIN = "bobMate";
    private static final String BOB_PASSWORD = "bob159363";
    private static final int BOB_AGE = 35;
    private static final String ALICE_LOGIN = "alice_Mate365";
    private static final String ALICE_PASSWORD = "ALICE__PASSWORD";
    private static final int ALICE_AGE = 22;
    private static final String JOHN_LOGIN = "HERE_IS_JOHNNY";
    private static final String JOHN_PASSWORD = "HAHA_HAHA";
    private static final int JOHN_AGE = 19;
    private static final String VALID_LOGIN = "denMate";
    private static final String VALID_PASSWORD = "den159363";
    private static final int VALID_AGE = 27;
    private static final String INVALID_LOGIN_LENGTH_FIVE = "abcde";
    private static final String INVALID_LOGIN_LENGTH_TWO = "xd";
    private static final String INVALID_PASSWORD_LENGTH_FIVE = "12345";
    private static final String INVALID_PASSWORD_LENGTH_TWO = "12";
    private static final int INVALID_AGE_SIX = 6;
    private static final int INVALID_AGE_TWELVE = 12;
    private static final String NULL_ITEM = null;
    private static final Integer NULL_AGE = null;

    private final User bob = new User();
    private final User alice = new User();
    private final User john = new User();
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        bob.setLogin(BOB_LOGIN);
        bob.setPassword(BOB_PASSWORD);
        bob.setAge(BOB_AGE);

        alice.setLogin(ALICE_LOGIN);
        alice.setPassword(ALICE_PASSWORD);
        alice.setAge(ALICE_AGE);

        john.setLogin(JOHN_LOGIN);
        john.setPassword(JOHN_PASSWORD);
        john.setAge(JOHN_AGE);

        Storage.people.add(bob);
        Storage.people.add(alice);
        Storage.people.add(john);
    }

    @Test
    void validData_Ok() {
        User den = new User();
        den.setLogin(VALID_LOGIN);
        den.setPassword(VALID_PASSWORD);
        den.setAge(VALID_AGE);

        User actual = registrationService.register(den);

        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void userIsNull_NotOk() {
        User nullUser = null;
        assertThrows(InvalidUserException.class, ()
                -> registrationService.register(nullUser));
    }

    @Test
    void existingLogin_NotOk() {
        User actual = new User();
        actual.setLogin(ALICE_LOGIN);
        actual.setPassword(ALICE_PASSWORD);
        actual.setAge(ALICE_AGE);

        assertThrows(LoginAlreadyExistsException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userLoginIsNull_NotOk() {
        User actual = new User();
        actual.setLogin(NULL_ITEM);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidLoginException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userLoginLengthFive_NotOk() {
        User actual = new User();
        actual.setLogin(INVALID_LOGIN_LENGTH_FIVE);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidLoginException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userLoginLengthTwo_NotOk() {
        User actual = new User();
        actual.setLogin(INVALID_LOGIN_LENGTH_TWO);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidLoginException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userPasswordIsNull_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(NULL_ITEM);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidPasswordException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userPasswordLengthFive_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(INVALID_PASSWORD_LENGTH_FIVE);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidPasswordException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userPasswordLengthTwo_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(INVALID_PASSWORD_LENGTH_TWO);
        actual.setAge(VALID_AGE);

        assertThrows(InvalidPasswordException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userAgeIsNull_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(NULL_AGE);

        assertThrows(InvalidAgeException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userAgeSix_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(INVALID_AGE_SIX);

        assertThrows(InvalidAgeException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void userAgeTwelve_NotOk() {
        User actual = new User();
        actual.setLogin(VALID_LOGIN);
        actual.setPassword(VALID_PASSWORD);
        actual.setAge(INVALID_AGE_TWELVE);

        assertThrows(InvalidAgeException.class, ()
                -> registrationService.register(actual));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}