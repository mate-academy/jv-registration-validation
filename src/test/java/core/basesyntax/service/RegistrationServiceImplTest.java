package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidArgumentException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.UserAlreadyExistException;
import core.basesyntax.model.User;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD = "ValidPassword";
    private static final int VALID_AGE = 20;
    private static final int MIN_VALID_AGE = 18;
    private static final String FIVE_CHARS_STRING = "12345";
    private static final String SIX_CHARS_STRING = "123456";
    private static final String EMPTY_STRING = "";
    private static RegistrationService registrationService;
    private User defaultUser;
    private Random random = new Random();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_validValues_Ok() {
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_nullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_underBoundAge_NotOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_minimumAge_Ok() {
        defaultUser.setAge(18);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundRandomAge_NotOk() {
        int underBoundRandomAge = random.nextInt(MIN_VALID_AGE);
        defaultUser.setAge(underBoundRandomAge);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_overBoundRandomAge_Ok() {
        int overBoundRandomAge = random.nextInt(82) + MIN_VALID_AGE;
        defaultUser.setAge(overBoundRandomAge);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_minimumLoginLength_Ok() {
        defaultUser.setLogin(SIX_CHARS_STRING);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundLoginLength_NotOk() {
        defaultUser.setLogin(FIVE_CHARS_STRING);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_emptyLogin_NotOk() {
        defaultUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_minimumPasswordLength_Ok() {
        defaultUser.setPassword(SIX_CHARS_STRING);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundPasswordLength_NotOk() {
        defaultUser.setPassword(FIVE_CHARS_STRING);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_emptyPassword_NotOk() {
        defaultUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidArgumentException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_twoEqualUsers_NotOk() {
        registrationService.register(defaultUser);
        assertThrows(UserAlreadyExistException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_threeDifferentValidUsers_Ok() {
        User bob = new User("Robert", "RobsPass", 33);
        User max = new User("SuperMax", "GOAT123", 25);
        User actualDefuultUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualDefuultUser);
        User actualBob = registrationService.register(bob);
        assertEquals(bob, actualBob);
        User actualMax = registrationService.register(max);
        assertEquals(max, actualMax);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
