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
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(String.valueOf(ValidationConstant.VALID_LOGIN));
        user.setPassword(String.valueOf(ValidationConstant.VALID_PASSWORD));
        user.setAge((Integer) ValidationConstant.VALID_AGE.getValue());
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
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with NULL password"
                        + " should throw IncorrectUserDataException.");

    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge((Integer) ValidationConstant.NEGATIVE_INVALID_AGE.getValue());
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age < 0"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageGreaterThenMaxAge_notOk() {
        user.setAge((Integer) ValidationConstant.INVALID_LARGE_AGE.getValue());
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age > 110"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageLessThenEighteen_notOk() {
        user.setAge((Integer) ValidationConstant.MIN_INVALID_AGE.getValue());
        assertThrows(UserDataException.class,
                () -> registrationService.register(user),
                "Registration user with age < 18"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageValidEighteen_ok() {
        user.setAge((Integer) ValidationConstant.MIN_VALID_AGE.getValue());
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registration user with age = 18 should add user to Storage.");
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
        user.setPassword(String.valueOf(ValidationConstant.INVALID_SHORT_PASSWORD.getValue()));
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
