package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final String WRONG_LOGIN_ZERO_SYMBOLS = "";
    private static final String WRONG_PASSWORD_ZERO_SYMBOLS = "";
    private static final String MINIMUM_CORRECT_LOGIN_ONE_SYMBOL = "a";
    private static final String ANOTHER_CORRECT_LOGIN = "aa";
    private static final String FIVE_SYMBOLS_WRONG_PASSWORD = "five5";
    private static final String MINIMUM_CORRECT_PASSWORD_SIX_SYMBOLS = "six456";
    private static final int MINIMUM_CORRECT_YEARS_OLD = 18;
    private static final int WRONG_YEARS_OLD = 17;
    private static final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(MINIMUM_CORRECT_LOGIN_ONE_SYMBOL);
        user.setPassword(MINIMUM_CORRECT_PASSWORD_SIX_SYMBOLS);
        user.setAge(MINIMUM_CORRECT_YEARS_OLD);
    }

    @Test
    void get_byExistingLogin_ok() {
        registrationService.register(user);
        User actual = storageDao.get(MINIMUM_CORRECT_LOGIN_ONE_SYMBOL);
        assertEquals(RegistrationServiceImplTest.user, actual, "Users must be equal");
    }

    @Test
    void get_byNonExistingLogin_notOk() {
        registrationService.register(user);
        User actual = storageDao.get(ANOTHER_CORRECT_LOGIN);
        assertNotEquals(RegistrationServiceImplTest.user, actual, "Users mustn't be equal");
    }

    @Test
    void register_null_notOk() {
        assertThrows(UserValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validData_Ok() {
        User expected = registrationService.register(user);
        assertEquals(expected, user, "Expected " + expected + ", but we have " + user);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroSymbolLogin_notOk() {
        user.setLogin(WRONG_LOGIN_ZERO_SYMBOLS);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_byExistingLogin_notOk() {
        registrationService.register(user);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_withoutSymbolsPassword_notOk() {
        user.setPassword(WRONG_PASSWORD_ZERO_SYMBOLS);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(FIVE_SYMBOLS_WRONG_PASSWORD);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeLess18_notOk() {
        user.setAge(WRONG_YEARS_OLD);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

