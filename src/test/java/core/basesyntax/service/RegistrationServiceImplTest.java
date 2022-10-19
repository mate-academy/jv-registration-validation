package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int NEGATIVE_USER_AGE = -20;
    private static final int VALID_USER_AGE = 20;
    private static final String INCORRECT_USER_PASSWORD = "1234";
    private static final String VALID_LOGIN = "Valid user login";
    private static final String VALID_PASSWORD = "Valid user password";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_USER_AGE);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_newUserValid_ok() {
        for (int i = 0; i < 3; i++) {
            user = new User();
            user.setLogin(VALID_LOGIN + i);
            user.setAge(VALID_USER_AGE + i);
            user.setPassword(VALID_PASSWORD + i);
            User actualUser = registrationService.register(user);
            assertEquals(user, actualUser, "The " + i + " valid user must be registered");
        }
        int expectedSize = 3;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_sameUser_notOk() {
        User sameFirstUser = new User();
        sameFirstUser.setLogin(user.getLogin());
        sameFirstUser.setAge(user.getAge());
        sameFirstUser.setPassword(user.getPassword());
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameFirstUser));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_USER_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_incorrectPassword_notOk() {
        user.setPassword(INCORRECT_USER_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
