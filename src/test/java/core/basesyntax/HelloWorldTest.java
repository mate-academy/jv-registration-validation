package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationFailureException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final int VALID_AGE = 20;
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD_FIRST_TYPE = "password";
    private static final String VALID_PASSWORD_SECOND_TYPE = "123456";
    private static final String INVALID_LOGIN = "login";
    private static final String INVALID_PASSWORD = "1234";
    private static final int ZERO_AGE = 0;
    private static final int INVALID_AGE = 17;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD_FIRST_TYPE);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_addUser_Ok() {
        User addedUser = registrationService.register(user);
        assertEquals(user, addedUser, "The valid user and result aren't the same.");
    }

    @Test
    void register_passwordEdges_Ok() {
        User addedUser = registrationService.register(user);
        assertEquals(user, addedUser, "User and dded User aren't the same.");

        Storage.people.clear();
        user.setPassword(VALID_PASSWORD_SECOND_TYPE);
        addedUser = registrationService.register(user);
        assertEquals(user, addedUser, "User and added User aren't the same.");
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Invalid result for null user.");
    }

    @Test
    void register_addIdenticalUsers_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added 2 identical users.");
    }

    @Test
    void register_addYoungUser_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user that younger then 18 y.o.");

        user.setAge(ZERO_AGE);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user that younger then 18 y.o.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user with null login.");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user with null password.");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user with null age.");
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user with short login.");
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(user),
                "Added user with short password");
    }
}
