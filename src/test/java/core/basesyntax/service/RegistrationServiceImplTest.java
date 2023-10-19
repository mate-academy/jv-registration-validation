package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        actual = new User();
    }

    @Test
    void register_validData_Ok() {
        User den = new User();
        den.setLogin(VALID_LOGIN);
        den.setPassword(VALID_PASSWORD);
        den.setAge(VALID_AGE);

        User expected = registrationService.register(den);;
        User actual = storageDao.get(den.getLogin());

        assertEquals(expected, actual);
    }

    @Test
    void register_userIsNull_NotOk() {
        actual = null;
        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_existingLogin_NotOk() {
        User bob = new User();
        bob.setLogin(BOB_LOGIN);
        bob.setPassword(BOB_PASSWORD);
        bob.setAge(BOB_AGE);

        User alice = new User();
        alice.setLogin(ALICE_LOGIN);
        alice.setPassword(ALICE_PASSWORD);
        alice.setAge(ALICE_AGE);

        User john = new User();
        john.setLogin(JOHN_LOGIN);
        john.setPassword(JOHN_PASSWORD);
        john.setAge(JOHN_AGE);

        storageDao.add(bob);
        storageDao.add(alice);
        storageDao.add(john);

        actual.setLogin(ALICE_LOGIN);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        actual.setLogin(NULL_ITEM);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userLoginLengthFive_NotOk() {
        actual.setLogin(INVALID_LOGIN_LENGTH_FIVE);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userLoginLengthTwo_NotOk() {
        actual.setLogin(INVALID_LOGIN_LENGTH_TWO);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        actual.setPassword(NULL_ITEM);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userPasswordLengthFive_NotOk() {
        actual.setPassword(INVALID_PASSWORD_LENGTH_FIVE);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userPasswordLengthTwo_NotOk() {
        actual.setPassword(INVALID_PASSWORD_LENGTH_TWO);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        actual.setAge(NULL_AGE);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userAgeSix_NotOk() {
        actual.setAge(INVALID_AGE_SIX);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @Test
    void register_userAgeTwelve_NotOk() {
        actual.setAge(INVALID_AGE_TWELVE);

        assertThrows(InvalidRegistrationException.class, ()
                -> registrationService.register(actual));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
