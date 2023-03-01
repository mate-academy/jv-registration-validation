package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.CurrentLoginIsExistsException;
import core.basesyntax.model.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "programator12341";
    private static final String DEFAULT_PASSWORD = "passatic123";
    private static final int DEFAULT_AGE = 19;
    private static final String SHORT_PASSWORD = "short";
    private static final String SHORT_LOGIN = "short";
    private static final String MISMATCH_LOGIN = "--------------";
    private static final String MISMATCH_PASSWORD = "%$#%@**(*&*(";

    private final RegistrationService registrationService = new RegistrationServiceImpl();
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
        StorageDaoImpl.setIndex(0L);
    }

    @Test
    void checkIfUserIsRegistered_Ok() {
        registrationService.register(defaultUser);
        Long actual = defaultUser.getId();
        assertEquals(1, actual);
    }

    @Test
    void checkCurrentLoginExistException_Ok() {
        registrationService.register(defaultUser);
        assertThrows(CurrentLoginIsExistsException.class, () ->
                        registrationService.register(defaultCopyUser),
                "CurrentLoginIsExist must thrown if current login is located in storage.");
    }

    @Test
    void checkInvalidInputDataIfUserNull_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(nullUser),
                "InvalidInputData must thrown if user is null or empty.");
    }

    @Test
    void checkInvalidInputDataExceptionIfLoginNullOrEmpty_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(nullLoginUser),
                "InvalidInputData must thrown if Login is null or empty.");
    }

    @Test
    void checkInvalidInputDataIfPasswordNullOrEmpty_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(nullPasswordUser),
                "InvalidInputData must thrown if Password is null or empty.");
    }

    @Test
    void checkShortPasswordException_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(shortPasswordUser),
                "InvalidInputData must thrown if Password is less than 10 elements.");
    }

    @Test
    void checkShortLoginException_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(shortLoginUser),
                "InvalidInputData must thrown if Login is less than 14 elements.");
    }

    @Test
    void checkMismatchPatternException_Ok() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(misMathcPatternUser),
                "InvalidInputData must thrown Login or Password mismatch pattern [a-z-A-Z-0-9].");
    }
}
