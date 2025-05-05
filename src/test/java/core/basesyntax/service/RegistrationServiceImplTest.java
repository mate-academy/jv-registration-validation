package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String TOO_SHORT_PASSWORD = "1234";
    private static final String SHORTEST_VALID_PASSWORD = "123456";
    private static final Integer AGE_LESS_THAN_MIN = 17;
    private static final Integer NEGATIVE_AGE = -20;
    private static final Integer MIN_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User defaultUser = new User(1111L, "login", "12345678", 19);

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void register_ageLessThanMin_notOk() {
        defaultUser.setAge(AGE_LESS_THAN_MIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_ageIsMin_Ok() {
        defaultUser.setAge(MIN_AGE);
        User registeredUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, registeredUser);
    }

    @Test
    public void register_negativeAge_notOk() {
        defaultUser.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_age_ok() {
        User registeredUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, registeredUser);
    }

    @Test
    public void register_passwordShorterThanMin_notOk() {
        defaultUser.setPassword(TOO_SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_shortestPassword_ok() {
        defaultUser.setPassword(SHORTEST_VALID_PASSWORD);
        User registeredUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, registeredUser);
    }

    @Test
    public void register_password_ok() {
        User registeredUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, registeredUser);
    }

    @Test
    public void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_emptyLogin_notOk() {
        defaultUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        storageDao.add(defaultUser);
        User duplicateUser = defaultUser;
        assertThrows(RegistrationException.class, () ->
                registrationService.register(duplicateUser));
    }

    @Test
    public void register_login_ok() {
        User registeredUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, registeredUser);
    }

    @Test
    public void register_nullUser_notOk() {
        defaultUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser));
    }
}
