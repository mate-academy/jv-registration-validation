package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static final int DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "user_password";
    private static final String DEFAULT_LOGIN = "user_login";
    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final char EMPTY_PLACE = ' ';
    private static final String SHORT_PASSWORD = "short";
    private static final int SMALL_AGE = 17;
    private static final int NEGATIVE_AGE = -68;
    private static final int BIG_AGE = 119;
    private static final int MAX_AGE = 118;
    private static final int NULL_AGE = 0;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
     void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_correct_ok() {
        user.setAge(MAX_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_maxAge_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(NULL_STRING);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(NULL_STRING);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPlacePassword_notOk() {
        user.setPassword(DEFAULT_PASSWORD + EMPTY_PLACE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPlaceLogin_notOk() {
        user.setPassword(DEFAULT_LOGIN + EMPTY_PLACE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_smallAge_notOk() {
        user.setAge(SMALL_AGE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_noAge_notOk() {
        user.setAge(NULL_AGE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_bigAge_notOk() {
        user.setAge(BIG_AGE);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userRegistered_notOk() {
        User userRegistered = user;
        registrationService.register(userRegistered);
        assertThrows(UserNotFoundException.class, () -> registrationService.register(user));

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
