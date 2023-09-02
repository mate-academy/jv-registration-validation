package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String MIN_LENGTH_VALID = "6chars";
    private static final String MIN_EDGE_LENGTH_VALID = "7 chars";
    private static final String MAX_EDGE_LENGTH_INVALID = "5char";
    private static final String IS_EMPTY = "";
    private static final String IS_BLANK = "      ";
    private static final String VALID_PASSWORD = "ValidPassword";
    private static final int VALID_AGE = 19;
    private static final int MIN_VALID_AGE = 18;
    private static final int MAX_INVALID_AGE = 17;
    private static final int AGE_IS_ZERO = 0;
    private static final int AGE_IS_NEGATIVE = -18;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();

    }

    @Test
    void register_ifLoginIsAbsent_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ifLoginIsPresent_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with current login already exists in the storage. "
                        + "The RegistrationException should be thrown");
    }

    @Test
    void register_LoginValidLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinValidLength_Ok() {
        user.setLogin(MIN_LENGTH_VALID);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinEdgeValidLength_Ok() {
        user.setLogin(MIN_EDGE_LENGTH_VALID);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login can't be null. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginIsEmpty_NotOk() {
        user.setLogin(IS_EMPTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login is empty. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginIsBlank_NotOk() {
        user.setLogin(IS_BLANK);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login is blank. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginMaxEdgeInvalidLength_NotOk() {
        user.setLogin(MAX_EDGE_LENGTH_INVALID);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordValidLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordMinValidLength_Ok() {
        user.setPassword(MIN_LENGTH_VALID);
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordMinEdgeValidLength_Ok() {
        user.setPassword(MIN_EDGE_LENGTH_VALID);
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password can't be null. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordIsEmpty_NotOk() {
        user.setPassword(IS_EMPTY);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password is empty. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordIsBlank_NotOk() {
        user.setPassword(IS_BLANK);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password is blank. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordMaxEdgeInvalidLength_NotOk() {
        user.setPassword(MAX_EDGE_LENGTH_INVALID);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_AgeValid_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void register_AgeMinEdgeValid_Ok() {
        user.setAge(MIN_VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void register_AgeMaxEdgeInvalid_NotOk() {
        user.setAge(MAX_INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }

    @Test
    void register_AgeIsZero_NotOk() {
        user.setAge(AGE_IS_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        user.setAge(AGE_IS_NEGATIVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }
}
