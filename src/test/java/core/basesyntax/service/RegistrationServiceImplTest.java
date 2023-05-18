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
    private static final String INC_LOGIN = "Login";
    private static final String INC_PASS = "Pass";
    private static final int INC_AGE = 15;

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
    void isNullUser_NotOk() {
        assertThrows(UserDataException.class,
                () -> registrationService.register(null),
                    "USER isn't NULL, should throw UserDataException.");
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "LOGIN is NULL, should throw UserDataException.");
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD is NULL, should throw UserDataException.");
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is NULL, should throw UserDataException.");
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "LOGIN is empty, should throw UserDataException.");
    }

    @Test
    void lessSixLogin_NotOk() {
        user.setLogin(INC_LOGIN);
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
    void emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD is empty, should throw UserDataException.");
    }

    @Test
    void lessSixPassword_NotOk() {
        user.setPassword(INC_PASS);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "PASSWORD must at least 6 characters, "
                                + "should throw UserDataException.");
    }

    @Test
    void lessMinExistAge_NotOk() {
        user.setAge(INC_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is NULL, should throw UserDataException.");
    }

    @Test
    void negativeAge_NotOk() {
        user.setAge(-INC_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                    "AGE is Negative, should throw UserDataException.");
    }

    @Test
    void invalidAge_NotOk() {
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
