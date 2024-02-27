package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
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
    private static final String INVALID_INPUT_ZERO_LENGTH = "";
    private static final String INVALID_INPUT_TWO_LENGTH = "ok";
    private static final String INVALID_INPUT_FIVE_LENGTH = "qwert";
    private static final int INVALID_AGE_FIVE = 5;
    private static final int INVALID_AGE_THIRTEEN = 13;
    private static final int VALID_AGE = 18;

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
    void nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null); });
    }

    @Test
    void nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void loginLessThanSixCharacters_notOk() {
        user.setLogin(INVALID_INPUT_ZERO_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setLogin(INVALID_INPUT_TWO_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setLogin(INVALID_INPUT_FIVE_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void passwordLessThanSixCharacters_notOk() {
        user.setPassword(INVALID_INPUT_ZERO_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setPassword(INVALID_INPUT_TWO_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setPassword(INVALID_INPUT_FIVE_LENGTH);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void invalidAge_notOk() {
        user.setAge(INVALID_AGE_FIVE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        user.setAge(INVALID_AGE_THIRTEEN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void sameLoginExists_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void validUserData_ok() {
        User expectedUser = new User();
        expectedUser.setLogin(VALID_LOGIN);
        expectedUser.setPassword(VALID_PASSWORD);
        expectedUser.setAge(VALID_AGE);
        assertNull(storageDao.get(expectedUser.getLogin()));
        User actualUser = registrationService.register(expectedUser);
        User storedUser = storageDao.get(expectedUser.getLogin());
        assertNotNull(storedUser);
        assertEquals(expectedUser, actualUser);
    }
}
