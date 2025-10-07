package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegistrationData;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int ADULT_AGE = 24;
    private static final int EXACT_MIN_AGE = 18;
    private static final int NOT_ADULT_AGE = 17;
    private static final int ZERO_AGE = 0;
    private static final int NEGATIVE_AGE = -5;
    private static final String VALID_LOGIN = "employee";
    private static final String LEN_3_LOGIN = "abc";
    private static final String LEN_5_LOGIN = "abcde";
    private static final String LEN_6_LOGIN = "abcdef";
    private static final String VALID_PASSWORD = "password";
    private static final String LEN_3_PASSWORD = "123";
    private static final String LEN_5_PASSWORD = "12345";
    private static final String LEN_6_PASSWORD = "123456";

    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(ADULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength5_notOk() {
        user.setLogin(LEN_5_LOGIN);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength6_ok() {
        user.setLogin(LEN_6_LOGIN);
        User saved = registrationService.register(user);
        assertEquals(user, saved);
        assertEquals(user, storageDao.get(LEN_6_LOGIN));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength5_notOk() {
        user.setPassword(LEN_5_PASSWORD);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength6_ok() {
        user.setPassword(LEN_6_PASSWORD);
        User saved = registrationService.register(user);
        assertEquals(user, saved);
        assertEquals(user, storageDao.get(VALID_LOGIN));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(ZERO_AGE);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_exactly18_ok() {
        user.setAge(EXACT_MIN_AGE);
        User saved = registrationService.register(user);
        assertNotNull(saved);
        assertEquals(user, storageDao.get(VALID_LOGIN));
    }

    @Test
    void register_validInput_ok() {
        User saved = registrationService.register(user);
        assertEquals(user, saved);
        assertEquals(user, storageDao.get(VALID_LOGIN));
    }

    @Test
    void register_duplicateLogin_notOk() {
        storageDao.add(user);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength3_notOk() {
        user.setLogin(LEN_3_LOGIN);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength3_notOk() {
        user.setPassword(LEN_3_PASSWORD);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }

    @Test
    void register_age17_notOk() {
        user.setAge(NOT_ADULT_AGE);
        assertThrows(InvalidRegistrationData.class, () -> registrationService.register(user));
    }
}
