package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void register_validLogin_ok() {
        user.setLogin(DEFAULT_LOGIN);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_validPassword_ok() {
        user.setPassword(DEFAULT_PASSWORD);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_validAge_ok() {
        user.setAge(DEFAULT_AGE);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_duplicateUser_notOk() {
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
    void register_loginLessThanSixCharacters_notOk() {
        user.setLogin(LESS_THAN_6_CHARACTERS);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordLessThanSixCharacters_notOk() {
        user.setPassword(LESS_THAN_6_CHARACTERS);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(UNDERAGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(NULL_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(NULL_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(NULL_INTEGER);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageZero_notOk() {
        user.setAge(ZERO_AGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginExactlySixCharacters_ok() {
        user.setLogin(SIX_CHARACTER_STRING);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordExactlySixCharacters_ok() {
        user.setPassword(SIX_CHARACTER_STRING);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageEighteen_Ok() {
        user.setAge(AGE_EIGHTEEN);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }
}
