package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_LOGIN_WITH_LENGTH_ZERO = "";
    private static final String INVALID_LOGIN_WITH_LENGTH_THREE = "sun";
    private static final String INVALID_LOGIN_WITH_LENGTH_FIVE = "abcde";
    private static final String INVALID_PASSWORD_WITH_LENGTH_ZERO = "";
    private static final String INVALID_PASSWORD_WITH_LENGTH_THREE = "sun";
    private static final String INVALID_PASSWORD_WITH_LENGTH_FIVE = "abcde";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE_THREE = 3;
    private static final int INVALID_AGE_ELEVEN = 11;
    private static final int INVALID_AGE_SEVENTEEN = 17;
    private static final int INVALID_NEGATIVE_AGE = -27;

    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null); });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_loginIsTooShort_notOk() {
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_passwordTooShort_notOk() {
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE_THREE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setAge(INVALID_AGE_ELEVEN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setAge(INVALID_AGE_SEVENTEEN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(INVALID_NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_theSameLoginExists_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_validUserData_ok() {
        User expectedUser = new User();
        expectedUser.setLogin(VALID_LOGIN);
        expectedUser.setPassword(VALID_PASSWORD);
        expectedUser.setAge(VALID_AGE);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }
}
