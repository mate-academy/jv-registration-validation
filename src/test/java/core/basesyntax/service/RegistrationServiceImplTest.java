package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static final String EDGE_LOGIN = "EdgeLo";
    private static final String INVALID_LOGIN = "invL";
    private static final String VALID_PASSWORD = "password";
    private static final String EDGE_PASSWORD = "EdgePs";
    private static final String INVALID_PASSWORD = "invP";
    private static final int VALID_USER_AGE = 20;
    private static final int USER_EDGE_AGE = 18;
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(VALID_USER_AGE);
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_valid_ok() {
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        assertEquals(validUser, actual);
    }

    @Test
    void register_edgeAge_ok() {
        validUser.setAge(USER_EDGE_AGE);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        assertEquals(validUser, actual);
    }

    @Test
    void register_edgePassword_ok() {
        validUser.setPassword(EDGE_PASSWORD);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        assertEquals(validUser, actual);
    }

    @Test
    void register_edgeLogin_ok() {
        validUser.setLogin(EDGE_LOGIN);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        assertEquals(validUser, actual);
    }

    @Test
    void register_underEdgeAge_notOk() {
        User invalidAgeUser = validUser;
        invalidAgeUser.setAge(USER_EDGE_AGE - 1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User invalidAgeUser = validUser;
        invalidAgeUser.setAge(-10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidPasswordUser = validUser;
        invalidPasswordUser.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        User invalidLoginUser = validUser;
        invalidLoginUser.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLoginUser));
    }

    @Test
    void register_nullAge_notOk() {
        User nullLoginAgeUser = validUser;
        nullLoginAgeUser.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginAgeUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginPasswordUser = validUser;
        nullLoginPasswordUser.setLogin(null);
        nullLoginPasswordUser.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginPasswordUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = validUser;
        nullPasswordUser.setLogin(VALID_LOGIN);
        nullPasswordUser.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_existedUser_notOk() {
        storageDao.add(validUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUser));
    }
}
