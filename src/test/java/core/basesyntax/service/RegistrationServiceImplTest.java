package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User actualUser = registrationService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void register_duplicateUser_notOk() {
        Storage.people.add(user);

        User duplicateUser = new User();
        duplicateUser.setLogin(DEFAULT_LOGIN);
        duplicateUser.setPassword(DEFAULT_PASSWORD);
        duplicateUser.setAge(DEFAULT_AGE);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(duplicateUser),
                "Expected register() to throw exception, but it didn't"
        );
        assertEquals(user.getLogin(), duplicateUser.getLogin(),
                "Duplicate user should not be registered");
    }

    @Test
    void register_loginTooShort_notOk() {
        user.setLogin(LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordTooShort_notOk() {
        user.setPassword(LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageTooYoung_notOk() {
        user.setAge(UNDERAGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageZero_notOk() {
        user.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginValidLength_ok() {
        user.setLogin(SIX_CHARACTER_STRING);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordValidLength_ok() {
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
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertNull(storageDao.get(user.getLogin()));
    }
}
