package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final User FIRST_USER = new User("Robert", "G123y45", 27);
    private static final User SECOND_USER = new User("Albert", "G123y41", 23);
    private static final User THIRD_USER = new User("Elisabete", "G123I44", 25);
    private static final User SAME_LOGIN_WITH_FIRST_USER = new User("Robert", "G123y45", 27);
    private static final User USER_WITH_LOGIN_LESS_THAN_SIX = new User("Mark", "G123y45", 27);
    private static final User USER_WITH_PASSWORD_LESS_THAN_SIX = new User("Mark", "G17", 27);
    private static final User USER_WITH_AGE_LESS_THAN_MIN_AGE = new User("Mark", "G17", 15);
    private static final User USER_WITH_NEGATIVE_AGE = new User("Mark", "G17", -1);
    private static final User USER_WITH_LOGIN_NULL = new User(null, "G123y45", 19);
    private static final User USER_WITH_PASSWORD_NULL = new User("Georg3", null, 25);
    private static final User USER_WITH_AGE_NULL = new User("Bernard", "G123", null);

    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void addUserToList_Ok() {
        storageDao.add(FIRST_USER);
        storageDao.add(SECOND_USER);
        storageDao.add(THIRD_USER);

        User actualFirstUser = storageDao.get(FIRST_USER.getLogin());
        User actualSecondUser = storageDao.get(SECOND_USER.getLogin());
        User actualThirdUser = storageDao.get(THIRD_USER.getLogin());

        assertEquals(FIRST_USER, actualFirstUser,
                "Test failed! First user should be " + FIRST_USER);
        assertEquals(SECOND_USER, actualSecondUser,
                "Test failed! Second user should be " + SECOND_USER);
        assertEquals(THIRD_USER, actualThirdUser,
                "Test failed! Third user should be " + THIRD_USER);
    }

    @Test
    public void testRegisterAddUserIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);

        });
    }

    @Test
    public void testRegisterAddUserWithSameLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(SAME_LOGIN_WITH_FIRST_USER);
        });
    }

    @Test
    public void testRegisterAddUserWithLoginOrPasswordLengthLessThanSix_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(USER_WITH_LOGIN_LESS_THAN_SIX);
            registrationService.register(USER_WITH_PASSWORD_LESS_THAN_SIX);
        });
    }

    @Test
    public void testRegisterAddUserWithAgeLessThanMinAge_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(USER_WITH_AGE_LESS_THAN_MIN_AGE);
        });
    }

    @Test
    public void testAddUserThanOneOfTheParametersNull_NotOk() {
        User userExpectedFirst = storageDao.add(USER_WITH_AGE_NULL);
        User userExpectedSecond = storageDao.add(USER_WITH_LOGIN_NULL);
        User userExpectedThird = storageDao.add(USER_WITH_PASSWORD_NULL);

        User actualFirstResultNull = storageDao.get(USER_WITH_AGE_NULL.getLogin());
        User actualSecondResultNull = storageDao.get(USER_WITH_LOGIN_NULL.getLogin());
        User actualThirdResultNull = storageDao.get(USER_WITH_PASSWORD_NULL.getLogin());

        assertNotEquals(userExpectedFirst, actualFirstResultNull,
                "Test failed! Age cannot be null");
        assertNotEquals(userExpectedSecond, actualSecondResultNull,
                "Test failed! Login cannot be null");
        assertNotEquals(userExpectedThird, actualThirdResultNull,
                "Test failed! Password cannot be null");
    }

    @Test
    public void testAddUserWithAgeLessThanMinAge_NotOk() {
        User userExpectedFirst = storageDao.add(USER_WITH_AGE_LESS_THAN_MIN_AGE);
        User userExpectedSecond = storageDao.add(USER_WITH_NEGATIVE_AGE);

        User actualFirstResultNull = storageDao.get(USER_WITH_AGE_LESS_THAN_MIN_AGE.getLogin());
        User actualSecondResultNull = storageDao.get(USER_WITH_NEGATIVE_AGE.getLogin());

        assertNotEquals(userExpectedFirst, actualFirstResultNull,
                "Test failed! Age cannot be less than 18");
        assertNotEquals(userExpectedSecond, actualSecondResultNull,
                "Test failed! Age cannot be less than 18");
    }

    @Test
    public void testAddUserWithAgeMoreThanMinAge_Ok() {
        storageDao.add(THIRD_USER);
        User expected = THIRD_USER;
        User actual = storageDao.get(THIRD_USER.getLogin());
        assertEquals(expected, actual, "Test failed! Age cannot be less than 18");
    }

    @Test
    public void testAddUserWithPasswordLessThanSix_NotOk() {

        User userExpectedFirst = storageDao.add(
                new User("Volodymyr", "", 25));
        User userExpectedSecond = storageDao.add(
                new User("Volodymyr", "abc", 25));
        User userExpectedThird = storageDao.add(
                new User("Volodymyr", "abcdf", 25));

        User userActualFirst = storageDao.get(userExpectedFirst.getLogin());
        User userActualSecond = storageDao.get(userExpectedSecond.getLogin());
        User userActualThird = storageDao.get(userExpectedThird.getLogin());

        assertNotEquals(userExpectedFirst, userActualFirst,
                "Test failed! Password length cannot be less than 6");
        assertNotEquals(userExpectedSecond, userActualSecond,
                "Test failed! Password length cannot be less than 6");
        assertNotEquals(userExpectedThird, userActualThird,
                "Test failed! Password length cannot be less than 6");

    }

    @Test
    public void testAddUserWithPasswordMoreThanSix_Ok() {
        User userExpectedWithPasswordSixSymbols = storageDao.add(
                new User("Volodymyr1", "abcdef", 25));
        User userExpectedWithPasswordEightSymbols = storageDao.add(
                new User("Volodymyr2", "abcdefgh", 25));
        User userExpectedWithPasswordTenSymbols = storageDao.add(
                new User("Daryna", "abcdefghij", 25));

        User userActualFirst = storageDao.get(userExpectedWithPasswordSixSymbols.getLogin());
        User userActualSecond = storageDao.get(userExpectedWithPasswordEightSymbols.getLogin());
        User userActualThird = storageDao.get(userExpectedWithPasswordTenSymbols.getLogin());

        assertEquals(userExpectedWithPasswordSixSymbols, userActualFirst,
                "Test failed! Password length cannot be less than 6");
        assertEquals(userExpectedWithPasswordEightSymbols, userActualSecond,
                "Test failed! Password length cannot be less than 6");
        assertEquals(userExpectedWithPasswordTenSymbols, userActualThird,
                "Test failed! Password length cannot be less than 6");

    }

}
