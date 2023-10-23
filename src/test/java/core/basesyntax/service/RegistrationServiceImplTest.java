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
    private static User nullLoginPasswordUser;
    private static User invalidAgeUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        nullLoginPasswordUser = new User();
        invalidAgeUser = new User();
    }

    @BeforeEach
    void setUp() {
        nullLoginPasswordUser.setAge(VALID_USER_AGE);
        nullLoginPasswordUser.setLogin(null);
        nullLoginPasswordUser.setPassword(null);
        invalidAgeUser.setLogin(VALID_LOGIN);
        invalidAgeUser.setPassword(VALID_PASSWORD);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_valid_ok() {
        User simpleValidUser = new User();
        simpleValidUser.setAge(VALID_USER_AGE);
        simpleValidUser.setLogin(VALID_LOGIN);
        simpleValidUser.setPassword(VALID_PASSWORD);
        User actual = registrationService.register(simpleValidUser);
        assertNotNull(actual);
        assertEquals(simpleValidUser, actual);
    }

    @Test
    void register_edgeAge_ok() {
        User edgeAgeUser = new User();
        edgeAgeUser.setAge(USER_EDGE_AGE);
        edgeAgeUser.setLogin("ValidEdgeAgeLogin");
        edgeAgeUser.setPassword(VALID_PASSWORD);
        User actual = registrationService.register(edgeAgeUser);
        assertNotNull(actual);
        assertEquals(edgeAgeUser, actual);
    }

    @Test
    void register_edgePassword_ok() {
        User edgePasswordUser = new User();
        edgePasswordUser.setAge(VALID_USER_AGE);
        edgePasswordUser.setLogin("ValidEdgePasswordLogin");
        edgePasswordUser.setPassword(EDGE_PASSWORD);
        User actual = registrationService.register(edgePasswordUser);
        assertNotNull(actual);
        assertEquals(edgePasswordUser, actual);
    }

    @Test
    void register_edgeLogin_ok() {
        User edgeLoginUser = new User();
        edgeLoginUser.setAge(VALID_USER_AGE);
        edgeLoginUser.setLogin(EDGE_LOGIN);
        edgeLoginUser.setPassword(VALID_PASSWORD);
        User actual = registrationService.register(edgeLoginUser);
        assertNotNull(actual);
        assertEquals(edgeLoginUser, actual);
    }

    @Test
    void register_underEdgeAge_notOk() {
        invalidAgeUser.setAge(USER_EDGE_AGE - 1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_negativeAge_notOk() {
        invalidAgeUser.setAge(-10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidPasswordUser = new User();
        invalidPasswordUser.setAge(VALID_USER_AGE);
        invalidPasswordUser.setLogin(VALID_LOGIN);
        invalidPasswordUser.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        User invalidLoginUser = new User();
        invalidLoginUser.setAge(VALID_USER_AGE);
        invalidLoginUser.setLogin(INVALID_LOGIN);
        invalidLoginUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLoginUser));
    }

    @Test
    void register_existedUser_notOk() {
        User existedUser = new User();
        existedUser.setAge(VALID_USER_AGE);
        existedUser.setLogin("ExistedLogin");
        existedUser.setPassword(VALID_PASSWORD);
        storageDao.add(existedUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(existedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginPasswordUser));
    }

    @Test
    void register_nullPassword_notOk() {
        nullLoginPasswordUser.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginPasswordUser));
    }
}
