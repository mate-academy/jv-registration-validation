package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String DEFAULT_VALID_LOGIN = "login";
    private static final String DEFAULT_VALID_PASSWORD = "password";
    private static final Integer DEFAULT_VALID_AGE = 21;
    private static final String ALL_SPECIAL_CHARACTERS = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final User TEST_USER = new User();
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        TEST_USER.setAge(DEFAULT_VALID_AGE);
        TEST_USER.setLogin(DEFAULT_VALID_LOGIN);
        TEST_USER.setPassword(DEFAULT_VALID_PASSWORD);
    }

    @Test
    void registerNullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userFieldsValueAreNull_NotOk() {
        TEST_USER.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Null age shouldn't be accepted.");
        TEST_USER.setAge(18);
        TEST_USER.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Null login shouldn't be accepted.");
        TEST_USER.setLogin("login");
        TEST_USER.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Null password shouldn't be accepted.");
        TEST_USER.setAge(null);
        TEST_USER.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Null fields shouldn't be accepted.");
    }

    @Test
    void registerUserWithAgeLessThan18_NotOk() {
        TEST_USER.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Users shouldn't be added with the age less than 18.");
    }

    @Test
    void LoginOfAnyCharacters_Ok() {
        TEST_USER.setPassword(ALL_SPECIAL_CHARACTERS);
        try {
            registrationService.register(TEST_USER);
        } catch (RuntimeException e) {
            fail("User adding must be possible with the login of any characters.\n"
                    + "But was: \n" + e);
        }
    }

    @Test
    void AgeEqualIntMaxValue_Ok() {
        TEST_USER.setAge(Integer.MAX_VALUE);
        try {
            registrationService.register(TEST_USER);
        } catch (RuntimeException e) {
            fail("User adding must be possible with the age equal integer MAX_VALUE. \n"
                    + "But was: \n" + e);
        }
    }

    @Test
    void PasswordOfAnyCharacters_Ok() {
        TEST_USER.setPassword(ALL_SPECIAL_CHARACTERS);
        try {
            registrationService.register(TEST_USER);
        } catch (RuntimeException e) {
            fail("User adding must be possible with the password of any characters.\n"
                    + "But was: \n" + e);
        }
    }

    @Test
    void passwordIsLessThanMinValue_NotOk() {
        TEST_USER.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Password shorter than 6 characters must not be accepted.");
    }

    @Test
    void userIsAlreadyExist_NotOk() {
        storageDao.add(TEST_USER);
        assertThrows(RuntimeException.class, () -> registrationService.register(TEST_USER),
                "Users shouldn't be added with the same login.");
    }

    @Test
    void registerUserWithValidValues_Ok() {
        User actual = registrationService.register(TEST_USER);
        System.out.println(actual);
        System.out.println(TEST_USER);
        assertEquals(TEST_USER, actual,
                "Method register must return added user.");
    }

    @Test
    void newUserIsInStorageAfterAdding_Ok() {
        User addedUser = registrationService.register(TEST_USER);
        String loginOfAddedUser = addedUser.getLogin();
        User actual = storageDao.get(loginOfAddedUser);
        assertEquals(actual, addedUser,
                "User must be in the storage after adding.");
    }
}