package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.CurrentLoginIsExists;
import core.basesyntax.model.InvalidInputData;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "programator12341";
    private static final String DEFAULT_PASSWORD = "passatic123";
    private static final int DEFAULT_AGE = 19;
    private static final int AGE_LIMIT = 18;
    private static final int LOGIN_MIN_LENGTH = 14;
    private static final int PASSWORD_MIN_LENGTH = 10;
    private static final String PATTERN_FOR_PASSWORD_LOGIN = "[A-Za-z0-9]*";
    private static final String SHORT_PASSWORD = "short";
    private static final String SHORT_LOGIN = "short";
    private static final String MISMATCH_LOGIN = "--------------";
    private static final String MISMATCH_PASSWORD = "%$#%@**(*&*(";

    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final Storage storage = new Storage();
    private final User defaultUser = new User();
    private final User defaultCopyUser = new User();
    private final User nullUser = new User();
    private final User nullPasswordUser = new User();
    private final User nullLoginUser = new User();
    private final User nullAgeUser = new User();
    private final User shortPasswordUser = new User();
    private final User shortLoginUser = new User();
    private final User misMathcPatternUser = new User();

    @BeforeEach
    void setUp() {
        defaultUser.setAge(18);
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);

        defaultCopyUser.setAge(DEFAULT_AGE);
        defaultCopyUser.setLogin(DEFAULT_LOGIN);
        defaultCopyUser.setPassword(DEFAULT_PASSWORD);

        nullUser.setAge(null);
        nullUser.setLogin(null);
        nullUser.setPassword(null);

        nullLoginUser.setPassword(DEFAULT_PASSWORD);
        nullLoginUser.setLogin(null);
        nullLoginUser.setAge(DEFAULT_AGE);

        nullPasswordUser.setAge(DEFAULT_AGE);
        nullPasswordUser.setLogin(DEFAULT_LOGIN);
        nullPasswordUser.setPassword(null);

        nullAgeUser.setLogin(DEFAULT_LOGIN);
        nullAgeUser.setPassword(DEFAULT_PASSWORD);

        shortLoginUser.setLogin(SHORT_LOGIN);
        shortLoginUser.setPassword(DEFAULT_PASSWORD);
        shortLoginUser.setAge(DEFAULT_AGE);

        shortPasswordUser.setLogin(DEFAULT_LOGIN);
        shortPasswordUser.setPassword(SHORT_PASSWORD);
        shortPasswordUser.setAge(DEFAULT_AGE);

        misMathcPatternUser.setPassword(MISMATCH_PASSWORD);
        misMathcPatternUser.setLogin(MISMATCH_LOGIN);
        misMathcPatternUser.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void afterEach() {
        storage.people.clear();
    }

    @Test
    void current_Login_Is_Not_Exists_In_Storage_Is_Ok() {
        boolean actual = storageDao.get(defaultUser.getLogin()) == null;
        assertTrue(actual);
    }

    @Test
    void checkLoginFieldIsNotEmptyOrNullIs_Ok() {
        boolean actual = defaultUser.getLogin() == null || defaultUser.getLogin().isEmpty();
        assertFalse(actual);
    }

    @Test
    void checkInputLoginLengthIs_Ok() {
        boolean actual = defaultUser.getLogin().length() >= LOGIN_MIN_LENGTH;
        assertTrue(actual);
    }

    @Test
    void checkLoginMatchPatternIs_Ok() {
        boolean actual = defaultUser.getLogin().matches(PATTERN_FOR_PASSWORD_LOGIN);
        assertTrue(actual);
    }

    @Test
    void currentPasswordIsNotEmptyOrNullIs_Ok() {
        boolean actual = defaultUser.getPassword() != null
                || !(defaultUser.getPassword().isEmpty());
        assertTrue(actual);
    }

    @Test
    void checkInputPasswordLengthIs_Ok() {
        boolean actual = defaultUser.getPassword().length() >= PASSWORD_MIN_LENGTH;
        assertTrue(actual);
    }

    @Test
    void checkPasswordMatchPatternIs_Ok() {
        boolean actual = defaultUser.getPassword().matches(PATTERN_FOR_PASSWORD_LOGIN);
        assertTrue(actual);
    }

    @Test
    void checkAgeValidation() {
        boolean actual = defaultUser.getAge() >= AGE_LIMIT;
        assertTrue(actual);
    }

    @Test
    void checkIfUserRegistered() {
        storageDao.add(defaultUser);
        boolean actual = storageDao.get(defaultUser.getLogin()).equals(defaultUser);
        assertTrue(actual);
    }

    @Test
    void checkCurrentLoginExistException() {
        registrationService.register(defaultUser);
        try {
            registrationService.register(defaultCopyUser);
        } catch (CurrentLoginIsExists exception) {
            return;
        }
        fail("CurrentLoginIsExist must thrown if current login is located in storage");
    }

    @Test
    void checkInvalidInputDataExceptionIfLoginNullOrEmpty() {
        try {
            registrationService.register(nullLoginUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Login is null or empty");
    }

    @Test
    void checkInvalidInputDataIfPasswordNullOrEmpty() {
        try {
            registrationService.register(nullPasswordUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Password is null or empty");
    }

    @Test
    void checkShortPasswordException() {
        try {
            registrationService.register(shortPasswordUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Password is less than 10 elements.");
    }

    @Test
    void checkShortLoginException() {
        try {
            registrationService.register(shortLoginUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Login is less than 14 elements.");
    }

    @Test
    void checkMismatchPatternException() {
        try {
            registrationService.register(misMathcPatternUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown Login or Password mismatch pattern [a-z-A-Z-0-9]");
    }
}
