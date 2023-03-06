package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN = "troll";
    private static final String EMPTY = "";
    private static final String PASSWORD = "qwerty12345";
    private static final Integer AGE = 18;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(LOGIN, PASSWORD, AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(null),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for user is null, but something went wrong!");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for login is null, but something went wrong!");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for password is null, but something went wrong!");
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for empty password, but something went wrong!");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for user age is null, but something went wrong!");
    }

    @Test
    void register_lessAge_notOk() {
        user.setAge(0);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for user age less than " + AGE
                        + ", but something went wrong!");
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        Storage.people.add(user);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Expected " + ValidationException.class.getSimpleName()
                        + " to be thrown for exist user login, but something went wrong!");
    }

    @Test
    void register_successful_ok() {
        User anotherUser = new User("gay", "111111", 45);
        Storage.people.add(anotherUser);
        Storage.people.add(user);
        assertEquals(user, Storage.people.get(Storage.people.size() - 1),
                "User with login '" + user.getLogin() + "' was not registered!!");
    }
}
