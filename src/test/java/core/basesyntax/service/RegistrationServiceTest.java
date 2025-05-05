package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String NULL_TEST_FAILURE_MESSAGE = "Null fields "
            + "should throw InvalidDataException!";

    private static final String LOGIN_EXISTS_FAILURE_MESSAGE = "Login already exists in storage "
            + "should throw InvalidDataException!";
    private static final String LOGIN_TEST_FAILURE_MESSAGE = "Login less than 6 letters "
            + "should throw InvalidDataException!";
    private static final String PASSWORD_TEST_FAILURE_MESSAGE = "Password less than 6 letters "
            + "should throw InvalidDataException!";
    private static final String AGE_TEST_FAILURE_MESSAGE = "Age less than 18 "
            + "or over 122 should throw InvalidDataException!";
    private static final String USER_BOB_LOGIN_DEFAULT = "bobjordan";
    private static final String USER_ALICE_LOGIN_DEFAULT = "alicebaker";
    private static final String LOGIN_WORSE_CASE = "bob";
    private static final String USER_PASSWORD_DEFAULT = "password";
    private static final String PASSWORD_WORSE_CASE = "pass";
    private static final Integer USER_AGE_DEFAULT = 18;
    private static final Integer LESS_AGE_WORSE_CASE = 16;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User bob;
    private User alice;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void createUser() {
        bob = new User();
        alice = new User();
    }

    @Test
    public void register_correctInputData_Ok() {
        bob.setLogin(USER_BOB_LOGIN_DEFAULT);
        bob.setPassword(USER_PASSWORD_DEFAULT);
        bob.setAge(USER_AGE_DEFAULT);
        alice.setLogin(USER_ALICE_LOGIN_DEFAULT);
        alice.setPassword(USER_PASSWORD_DEFAULT);
        alice.setAge(USER_AGE_DEFAULT);
        registrationService.register(bob);
        registrationService.register(alice);
        User expectedFirstUser = storageDao.get(bob.getLogin());
        User expectedSecondUser = storageDao.get(alice.getLogin());
        assertEquals(expectedFirstUser, bob);
        assertEquals(expectedSecondUser, alice);
    }

    @Test
    public void register_duplicatedUser_notOk() {
        bob.setLogin(USER_BOB_LOGIN_DEFAULT);
        bob.setPassword(USER_PASSWORD_DEFAULT);
        bob.setAge(USER_AGE_DEFAULT);
        alice = bob;
        registrationService.register(bob);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(alice), LOGIN_EXISTS_FAILURE_MESSAGE);
    }

    @Test
    public void register_duplicatedUserLogin_notOk() {
        bob.setLogin(USER_BOB_LOGIN_DEFAULT);
        bob.setPassword(USER_PASSWORD_DEFAULT);
        bob.setAge(USER_AGE_DEFAULT);
        alice.setLogin(USER_BOB_LOGIN_DEFAULT);
        alice.setPassword(PASSWORD_WORSE_CASE);
        alice.setAge(LESS_AGE_WORSE_CASE);
        registrationService.register(bob);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(alice), LOGIN_EXISTS_FAILURE_MESSAGE);
    }

    @Test
    public void register_invalidUserLogin_notOk() {
        bob.setLogin(LOGIN_WORSE_CASE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(bob), LOGIN_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_invalidUserPass_notOk() {
        bob.setLogin(USER_BOB_LOGIN_DEFAULT);
        bob.setPassword(PASSWORD_WORSE_CASE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(bob), PASSWORD_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_invalidUserAge_notOk() {
        alice.setLogin(USER_ALICE_LOGIN_DEFAULT);
        alice.setPassword(USER_PASSWORD_DEFAULT);
        alice.setAge(LESS_AGE_WORSE_CASE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(alice), AGE_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_nullUserLogin_notOk() {
        bob.setLogin(null);
        bob.setPassword(USER_PASSWORD_DEFAULT);
        bob.setAge(USER_AGE_DEFAULT);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(bob), NULL_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_nullUserPass_notOk() {
        alice.setLogin(USER_ALICE_LOGIN_DEFAULT);
        alice.setPassword(null);
        alice.setAge(USER_AGE_DEFAULT);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(alice), NULL_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_nullUserAge_notOk() {
        bob.setLogin(USER_BOB_LOGIN_DEFAULT);
        bob.setPassword(USER_PASSWORD_DEFAULT);
        bob.setAge(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(bob), NULL_TEST_FAILURE_MESSAGE);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(null), NULL_TEST_FAILURE_MESSAGE);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }
}
