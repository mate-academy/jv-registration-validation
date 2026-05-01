package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class TestRegistrationService {
    private static final int DEF_AGE = 18;

    private static final String DEF_PASS = "123456";
    private static final String LONGER_PASS = "12345678";
    private static final String SHORT_PASS = "12345";
    private static final String SHORTER_PASS = "123";
    private static final String BLANK_PASS = "";

    private static final String DEF_LOGIN = "123456";
    private static final String DUPLICATE_LOGIN = "1234567";
    private static final String LONGER_LOG = "12345678";
    private static final String SHORT_LOG = "12345";
    private static final String SHORTER_LOG = "123";
    private static final String BLANK_LOG = "";

    private static final int LOW_AGE = 17;

    private User correctUser;

    private User originUser;
    private User duplicateUser;

    private User nullUser;
    private User nullLogUser;
    private User nullPassUser;
    private User nullAgeUser;

    private User shortLogUser;
    private User longerLogUser;
    private User shorterLogUser;
    private User blankLogUser;

    private User shortPassUser;
    private User longerPassUser;
    private User shorterPassUser;
    private User blankPassUser;

    private User lowAgeUser;
    private RegistrationService registrationService;
    private StorageDao storageDao;

    private static User createUser(String login, String password, Integer age) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAge(age);
        return newUser;
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        correctUser = createUser(DEF_LOGIN, DEF_PASS, DEF_AGE);
        duplicateUser = createUser(DUPLICATE_LOGIN, DEF_PASS, DEF_AGE);
        originUser = createUser(DUPLICATE_LOGIN, LONGER_PASS, DEF_AGE);

        nullUser = null;
        nullLogUser = createUser(null, DEF_PASS, DEF_AGE);
        nullPassUser = createUser(DEF_LOGIN, null, DEF_AGE);
        nullAgeUser = createUser(DEF_LOGIN, DEF_PASS, null);

        shortLogUser = createUser(SHORT_LOG, DEF_PASS, DEF_AGE);
        longerLogUser = createUser(LONGER_LOG, DEF_PASS, DEF_AGE);
        shorterLogUser = createUser(SHORTER_LOG, DEF_PASS, DEF_AGE);
        blankLogUser = createUser(BLANK_LOG, DEF_PASS, DEF_AGE);

        shortPassUser = createUser(DEF_LOGIN, SHORT_PASS, DEF_AGE);
        longerPassUser = createUser(DEF_LOGIN, LONGER_PASS, DEF_AGE);
        shorterPassUser = createUser(DEF_LOGIN, SHORTER_PASS, DEF_AGE);
        blankPassUser = createUser(DEF_LOGIN, BLANK_PASS, DEF_AGE);

        shortPassUser = createUser(DEF_LOGIN, SHORTER_PASS, DEF_AGE);

        lowAgeUser = createUser(DEF_LOGIN, DEF_PASS, LOW_AGE);
    }

    @AfterEach
    void cleanUp() {
        Storage.people.clear();
    }

    @Test
    void register_User_Ok() {
        User actual = registrationService.register(correctUser);
        User expected = storageDao.get(correctUser.getLogin());
        assertEquals(actual, expected);
        assertNotNull(actual.getId());
    }

    @Test
    void register_SameLogin_NotOk() {
        storageDao.add(originUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicateUser));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_NullFieldsUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(nullLogUser));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPassUser));
        assertThrows(RegistrationException.class, () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_LogLongerUser_Ok() {
        User actual = registrationService.register(longerLogUser);
        User expected = storageDao.get(longerLogUser.getLogin());
        assertEquals(actual, expected);
        assertNotNull(actual.getId());
    }

    @Test
    void register_LogShortUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortLogUser));
    }

    @Test
    void register_LogShorterUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shorterLogUser));
    }

    @Test
    void register_LogBlankUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(blankLogUser));
    }

    @Test
    void register_PassLongerUser_Ok() {
        User actual = registrationService.register(longerPassUser);
        User expected = storageDao.get(longerPassUser.getLogin());
        assertEquals(actual, expected);
        assertNotNull(actual.getId());
    }

    @Test
    void register_PassShortUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPassUser));
    }

    @Test
    void register_PassShorterUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shorterPassUser));
    }

    @Test
    void register_PassBlankUser_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(blankPassUser));
    }

    @Test
    void register_LowAgeUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(lowAgeUser));
    }
}
