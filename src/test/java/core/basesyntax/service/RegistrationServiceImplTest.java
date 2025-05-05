package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private static RegistrationServiceImpl registrationService;
    private static final int SUCCESS_AGE = 20;
    private static final int INVALID_AGE = 120;
    private static final String SUCCESS_LOGIN = "SuccessLogin";
    private static final String SUCCESS_PASS = "SuccessPassword";
    private static final String INCORRECT_LOGIN = "Login";
    private static final String INCORRECT_PASS = "Pass";
    private static final int INCORRECT_AGE = 15;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLogin(SUCCESS_LOGIN);
        user.setPassword(SUCCESS_PASS);
        user.setAge(SUCCESS_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserDataException.class,
                () -> registrationService.register(null),
                    "USER isn't NULL, should throw UserDataException.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "LOGIN is NULL, should throw UserDataException.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD is NULL, should throw UserDataException.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is NULL, should throw UserDataException.");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "LOGIN is empty, should throw UserDataException.");
    }

    @Test
    void register_lessSixLogin_notOk() {
        user.setLogin(INCORRECT_LOGIN);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "LOGIN must at least 6 characters, "
                                + "should throw UserDataException.");
    }

    @Test
    void register_userExists_notOk() {
        Storage.people.add(user);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "USER with same login"
                                + " should throw UserDataException.");
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD is empty, should throw UserDataException.");
    }

    @Test
    void register_lessSixPassword_notOk() {
        user.setPassword(INCORRECT_PASS);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD must at least 6 characters, "
                                + "should throw UserDataException.");
    }

    @Test
    void register_lessMinExistAge_notOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is NULL, should throw UserDataException.");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-INCORRECT_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is Negative, should throw UserDataException.");
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is Negative, should throw UserDataException.");
    }

    @Test
    void register_correctUser_ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                    "Registering user should add user to Storage.");
    }
}
