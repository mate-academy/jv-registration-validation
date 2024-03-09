package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.UserRegistrationException;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "test_login_created_at " + LocalDateTime.now();
    private static final String VALID_PASSWORD = "Mine!$VAl1DP@ZSW0Rd!)(*&^%$#@";
    private static final int VALID_AGE = 20;
    private static final String VALID_EDGE_LOGIN = "testLo";
    private static final String VALID_EDGE_PASSWORD = "MiP@33";
    private static final int VALID_EDGE_AGE = 18;
    private static final String INVALID_SHORT_LOGIN = "testL";
    private static final String INVALID_SHORT_PASSWORD = "MiP@3";
    private static final int INVALID_AGE = 17;
    private static final String INVALID_EMPTY_LOGIN = "";
    private static final String INVALID_EMPTY_PASSWORD = "";
    private static final int INVALID_ZERO_AGE = 0;

    private RegistrationServiceImpl registrationService;
    private User user;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullLogin_notOK() {
        user = new User(null, VALID_PASSWORD, VALID_EDGE_AGE);
        boolean result = registrationService.checkLogin(user);
        assertFalse(result,"Login cannot be null");
    }

    @Test
    public void register_nullPass_notOK() {
        user = new User(VALID_LOGIN, null, VALID_AGE);
        boolean result = registrationService.checkPassword(user);
        assertFalse(result,"Password cannot be null");
    }

    @Test
    public void register_nullAge_notOK() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        boolean result = registrationService.checkAge(user);
        assertFalse(result);
    }

    @Test
    public void register_LoginExeption_notOk() {
        user = new User(null, VALID_PASSWORD, INVALID_ZERO_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_NullPassExeption_notOk() {
        user = new User(VALID_LOGIN, null, INVALID_ZERO_AGE);
        try {
            registrationService.register(user);
        } catch (UserRegistrationException e) {
            return;
        }
    }

    @Test
    public void register_InvalidAgeExeption_notOk() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_ZERO_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_EdgeLogin_OK() {
        user = new User(VALID_EDGE_LOGIN, VALID_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkLogin(user);
        assertTrue(result);
    }

    @Test
    public void register_EdgePass_OK() {
        user = new User(VALID_PASSWORD, VALID_EDGE_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkPassword(user);
        assertTrue(result);
    }

    @Test
    public void register_EdgeAge_OK() {
        user = new User(VALID_PASSWORD, VALID_PASSWORD, VALID_EDGE_AGE);
        boolean result = registrationService.checkAge(user);
        assertTrue(result);
    }

    @Test
    public void register_AllEdgeData_OK() {
        user = new User(VALID_EDGE_LOGIN, VALID_EDGE_PASSWORD, VALID_EDGE_AGE);
        boolean result = registrationService.checkLogin(user);
        assertTrue(result);
    }

    @Test
    public void registet_UserNotExist_OK() {
        user = new User("nonExistingUser", "password", 20);
        boolean result = registrationService.userExists(user);
        assertFalse(result);
    }

    @Test
    public void register_shortPass_notOK() {
        user = new User(VALID_LOGIN, INVALID_SHORT_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkPassword(user);
        assertFalse(result);
    }

    @Test
    public void register_shortLogin_notOK() {
        user = new User(INVALID_SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkLogin(user);
        assertFalse(result);
    }

    @Test
    public void register_underAge_notOK() {
        user = new User(VALID_EDGE_LOGIN, VALID_PASSWORD, INVALID_AGE);
        boolean result = registrationService.checkAge(user);
        assertFalse(result);
    }

    @Test
    public void register_emptyPass_notOK() {
        user = new User(VALID_LOGIN, INVALID_EMPTY_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkPassword(user);
        assertFalse(result);
    }

    @Test
    public void register_emptyLogin_notOK() {
        user = new User(INVALID_EMPTY_LOGIN, VALID_PASSWORD, VALID_AGE);
        boolean result = registrationService.checkLogin(user);
        assertFalse(result);
    }

    @Test
    public void register_zeroAge_notOK() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_ZERO_AGE);
        boolean result = registrationService.checkAge(user);
        assertFalse(result);
    }

    @AfterAll
    static void noWitnesses() {
        Storage.people.clear();
    }
}
