package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "VALID_PASSWORD";
    private static final String INVALID_SHORT_PASSWORD = "ISP";
    private static final Integer VALID_AGE = 30;
    private static final Integer MIN_VALID_AGE = 18;
    private static final Integer MIN_INVALID_AGE = 16;
    private static final Integer NEGATIVE_INVALID_AGE = -10;
    private static final Integer INVALID_LARGE_AGE = 130;
    private static final String VALID_LOGIN = "VALID_LOGIN";
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registration valid user should add user to Storage.");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserDataException.class,
                () -> registrationService.register(null),
                "Registration NULL user should throw IncorrectUserDataException.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with NULL login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with NULL age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_INVALID_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age < 0"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageGreaterThenMaxAge_notOk() {
        user.setAge(INVALID_LARGE_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age > 110"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageLessThenEighteen_notOk() {
        user.setAge(MIN_INVALID_AGE);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age < 18"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageValidEighteen_ok() {
        user.setAge(MIN_VALID_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registration user with age = 18 should add user to Storage.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with NULL password"
                        + " should throw IncorrectUserDataException.");

    }

    @Test
    void register_userExists_notOk() {
        Storage.people.add(user);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with login that already exists"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with empty login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_passwordLessThenSix_notOk() {
        user.setPassword(INVALID_SHORT_PASSWORD);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with password length less than six"
                        + " should throw IncorrectUserDataException.");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
