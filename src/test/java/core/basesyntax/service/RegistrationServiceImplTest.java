package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 14;
    private static final int NEGATIVE_AGE = -18;
    private static final int MAX_VALID_AGE = 110;
    private static final String VALID_LOGIN = "geronimo";
    private static final String VALID_PASSWORD = "ziggurat_687";
    private static final String INVALID_PASSWORD = "o7 07";
    private static final String EMPTY_LINE = "";
    private static RegistrationService service;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(INVALID_AGE);
        user.setLogin(EMPTY_LINE);
        user.setPassword(INVALID_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(ValidationException.class, () -> service.register(user),
                "User can't be null.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Login can't be null.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Age can't be null.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Password can't be null.");
    }

    @Test
    void register_loginIsOccupied_notOk() {
        user.setLogin(VALID_LOGIN);
        Storage.people.add(user);
        User testUser = new User();
        testUser.setLogin(user.getLogin());
        assertThrows(ValidationException.class, () -> service.register(testUser),
                "This login already exists");
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_LINE);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Password can't be empty.");
    }

    @Test
    void register_emptyLogin_notOk() {
        assertThrows(ValidationException.class, () -> service.register(user),
                "Login can't be empty.");
    }

    @Test
    void register_userAgeUnder18_notOk() {
        assertThrows(ValidationException.class, () -> service.register(user),
                "Age of user is invalid.");
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Age of user is invalid.");
    }

    @Test
    void register_userAgeIsBiggerThanMax_notOk() {
        user.setAge(MAX_VALID_AGE + 1);
        assertThrows(ValidationException.class, () -> service.register(user),
                "Age of user is invalid.");
    }

    @Test
    void register_passwordLengthLess6_notOk() {
        assertThrows(ValidationException.class, () -> service.register(user),
                "Password must contains at least 6 characters.");
    }

    @Test
    void register_userWithValidData_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertDoesNotThrow(() -> service.register(user),
                "User is added to storage.");
    }
}
