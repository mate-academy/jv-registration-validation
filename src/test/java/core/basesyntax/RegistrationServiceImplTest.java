package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final Integer VALID_AGE = 20;
    private static final String VALID_EDGECASE_LOGIN = "login6";
    private static final String VALID_EDGECASE_PASSWORD = "pass66";
    private static final Integer VALID_EDGECASE_AGE = 18;
    private static final String INVALID_EDGECASE_LOGIN = "login";
    private static final String INVALID_EDGECASE_PASSWORD = "pass6";
    private static final Integer INVALID_EDGECASE_AGE = 17;
    private static final String INVALID_LOGIN = " ";
    private static final String INVALID_PASSWORD = " ";
    private static final Integer INVALID_AGE = 1;
    private static final Integer INVALID_NEGATIVE_AGE = -5;
    private static final String INVALID_NULL_LOGIN = null;
    private static final String INVALID_NULL_PASSWORD = null;
    private static final Integer INVALID_NULL_AGE = null;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void close() {
        Storage.people.clear();
    }

    @Test
    void register_validCredentials_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void registrationServiceWork_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_validCredentialsEdgeCase_Ok() {
        user.setLogin(VALID_EDGECASE_LOGIN);
        user.setPassword(VALID_EDGECASE_PASSWORD);
        user.setAge(VALID_EDGECASE_AGE);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_loginLengthLessThanRequiredEdgeCase_NotOk() {
        user.setLogin(INVALID_EDGECASE_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThanRequiredEdgeCased_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_EDGECASE_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanRequiredEdgeCase_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_EDGECASE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_NotOk() {
        user.setLogin(INVALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(INVALID_NULL_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_NULL_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_NULL_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
