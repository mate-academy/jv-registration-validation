package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.UserRegistrationException;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "test_login_created_at " + LocalDateTime
            .now()
            .format(DateTimeFormatter
            .ofPattern("yyyy-MM-dd-HH"));
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

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @AfterEach
    void noWitnesses() {
        Storage.people.clear();
    }

    @Test
    public void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Login field cannot be null");
    }

    @Test
    public void register_nullPassword_notOK() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Password field cannot be null");
    }

    @Test
    public void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Age field cannot be null");
    }

    @Test
    public void register_NullLoginExeption_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        }, "Login cannot be null, exception about login check failed should see you");
    }

    @Test
    public void register_NullPasswordExeption_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        }, "Password cannot be null, exception about password check failed should see you");
    }

    @Test
    public void register_ZeroAge_notOk() {
        user.setAge(INVALID_ZERO_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        }, "Age cannot be zero");
    }

    @Test
    public void register_EdgeLogin_OK() {
        user.setLogin(VALID_EDGE_LOGIN);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login lenght 6 should be acceptable");
    }

    @Test
    public void register_EdgePassword_OK() {
        user.setPassword(VALID_EDGE_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password lenght 6 should be acceptable");
    }

    @Test
    public void register_EdgeAge_OK() {
        user.setAge(VALID_EDGE_AGE);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Age 18 should be acceptable");
    }

    @Test
    public void register_AllEdgeData_OK() {
        user.setLogin(VALID_EDGE_LOGIN);
        user.setPassword(VALID_EDGE_PASSWORD);
        user.setAge(VALID_EDGE_AGE);
        assertDoesNotThrow(() -> registrationService.register(user),"User with "
                + "minimal lenght login"
                + "minimal lenght password"
                + "Age = 18 should be acceptable");
    }

    @Test
    public void register_shortPassword_notOK() {
        user.setPassword(INVALID_SHORT_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Password field cannot be less than 6 characters");
    }

    @Test
    public void register_shortLogin_notOK() {
        user.setLogin(INVALID_SHORT_LOGIN);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Login field cannot be less than 6 characters");
    }

    @Test
    public void register_underAge_notOK() {
        user.setAge(INVALID_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Age cannot be lover than 18");
    }

    @Test
    public void register_emptyPassword_notOK() {
        user.setPassword("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Password field cannot be empty");
    }

    @Test
    public void register_emptyLogin_notOK() {
        user.setLogin("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        },"Login field cannot be empty");
    }

    @Test
    public void register_userAlreadyExist_notOK() {
        Storage.people.add(user);
        User user2 = new User(VALID_LOGIN, VALID_PASSWORD,VALID_AGE);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user2);
        },"User already exist");
    }

    @Test
    public void register_userNull_notOK() {
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(null);
        },"User cannot be null");
    }
}
