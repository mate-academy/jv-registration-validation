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
    private static final Integer DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_commonUser_ok() {
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registering user should add user to Storage.");
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
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with null login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with NULL age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with negative age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_greaterOneHundred_notOk() {
        user.setAge(101);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with negative age"
                        + " should should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageLessEighteen_notOk() {
        user.setAge(17);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with age less than 18"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_ageGreaterEighteen_ok() {
        user.setAge(18);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user),
                "Registering user with age 18 should add user to Storage.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with null password"
                        + " should throw IncorrectUserDataException.");

    }

    @Test
    void register_userExists_notOk() {
        Storage.people.add(user);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with same login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with empty login"
                        + " should throw IncorrectUserDataException.");
    }

    @Test
    void register_passwordLessSix_notOk() {
        user.setPassword("12345");
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "Registering user with password length less than six"
                        + " should throw IncorrectUserDataException.");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
