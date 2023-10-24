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
    private static Storage storage;
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
        storage.people.clear();
    }

    @Test
    void register_User_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_User_EdgeCase_Ok() {
        user.setLogin(VALID_EDGECASE_LOGIN);
        user.setPassword(VALID_EDGECASE_PASSWORD);
        user.setAge(VALID_EDGECASE_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_User_Invalid_EdgeCase_Login_NotOk() {
        user.setLogin(INVALID_EDGECASE_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_EdgeCase_Password_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_EDGECASE_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_EdgeCase_Age_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_EDGECASE_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Login_NotOk() {
        user.setLogin(INVALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Password_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Age_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Negative_Age_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_NEGATIVE_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Null_Login_NotOk() {
        user.setLogin(INVALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Null_Password_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(INVALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User_Invalid_Null_Age_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(INVALID_NULL_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
