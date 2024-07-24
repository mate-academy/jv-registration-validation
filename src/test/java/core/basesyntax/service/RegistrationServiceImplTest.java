package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "defaultLogin";
    private static final String DEFAULT_PASSWORD = "defaultPassword";
    private static final Integer DEFAULT_AGE = 36;
    private static final String LESS_THAN_6_CHARACTERS = "short";
    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = null;
    private static final Integer NULL_INTEGER = null;
    private static final Integer ZERO_AGE = 0;
    private static final Integer UNDERAGE = 17;
    private static final Integer AGE_EIGHTEEN = 18;
    private static final Integer NEGATIVE_AGE = -1;
    private static final String SIX_CHARACTER_STRING = "six123";
    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @ AfterEach
    void tearDown() {
        storageDao.clear();
    }

    @Test
    void checkLengthOfUsersLogin_Ok() {
        user.setLogin(DEFAULT_LOGIN);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void checkLengthOfUsersPassword_Ok() {
        user.setPassword(DEFAULT_PASSWORD);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void checkCorrectAge_Ok() {
        user.setAge(DEFAULT_AGE);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void addUserWhoAlreadyExist_NotOk() {
        registrationService.register(user);

        User duplicateUser = new User();
        duplicateUser.setLogin(DEFAULT_LOGIN);
        duplicateUser.setPassword(DEFAULT_PASSWORD);
        duplicateUser.setAge(DEFAULT_AGE);

        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(duplicateUser),
                "Expected register() to throw exception, but it didn't"
        );

        int userCount = 0;
        for (User user : Storage.people) {
            if (user.getLogin().equals(DEFAULT_LOGIN)) {
                userCount++;
            }
        }
        assertEquals(1, userCount, "Duplicate user should not be registered");
    }

    @Test
    void ifLoginLessThanSixCharacters_NotOk() {
        user.setLogin(LESS_THAN_6_CHARACTERS);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifPasswordLessThanSixCharacters_NotOk() {
        user.setPassword(LESS_THAN_6_CHARACTERS);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifAgeLessThan18_NotOk() {
        user.setAge(UNDERAGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void testNullLogin_NotOk() {
        user.setLogin(NULL_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void testNullPassword_NotOk() {
        user.setPassword(NULL_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void testNullAge_NotOk() {
        user.setAge(NULL_INTEGER);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void testAgeZero_NotOk() {
        user.setAge(ZERO_AGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifLoginExactlySixCharacters_Ok() {
        user.setLogin(SIX_CHARACTER_STRING);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifPasswordExactlySixCharacters_Ok() {
        user.setPassword(SIX_CHARACTER_STRING);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifAgeExactlyEighteen_Ok() {
        user.setAge(AGE_EIGHTEEN);
        registrationService.register(user);
        assertTrue(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifLoginIsEmpty_NotOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifPasswordIsEmpty_NotOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }

    @Test
    void ifAgeIsNegative_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertFalse(registrationService.isSuccesfullyRegistered(user));
    }
}
