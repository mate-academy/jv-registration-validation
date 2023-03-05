package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final Integer DEFAULT_AGE = 20;
    private static final Integer NEGATIVE_AGE = -1;
    private static final Integer GREATER_THAN_MAX_AGE = 101;
    private static final Integer LOWER_THAN_MIN_AGE = 17;
    private static final Integer MIN_AGE = 18;
    private static final String DEFAULT_LOGIN = "login";
    private static final String EMPTY_LOGIN = "";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static final String TOO_SHORT_PASSWORD = "12345";
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        Storage.people.clear();
    }

    @Test
    void register_nullObject_notOk() {
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(null),
                "Registering NULL user should throw IncorrectUserDataException.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with null login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with NULL age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(NEGATIVE_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with negative age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_greaterOneHundred_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(GREATER_THAN_MAX_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with negative age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageLessEighteen_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(LOWER_THAN_MIN_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with age less than 18"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageGreaterEighteen_ok() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registering user with age 18 should add user to Storage.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(null);
        user.setAge(DEFAULT_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with null password"
                        + " should throw IncorrectUserDataException.");

    }

    @Test
    void register_userExists_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        Storage.people.add(user);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with same login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(EMPTY_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with empty login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_passwordLessSix_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(TOO_SHORT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with password length less than six"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_commonUser_ok() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registering user should add user to Storage.");
    }
}
