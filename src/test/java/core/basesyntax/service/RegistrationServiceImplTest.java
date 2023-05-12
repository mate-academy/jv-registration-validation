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
    private static final int VALID_AGE_RANGE = 82;
    private User defaultUser;
    private Random random;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        defaultUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_validValues_ok() {
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_underBoundAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_minimumAge_ok() {
        defaultUser.setAge(18);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundRandomAge_notOk() {
        random = new Random();
        int underBoundRandomAge = random.nextInt(MIN_VALID_AGE);
        defaultUser.setAge(underBoundRandomAge);
        assertThrows(InvalidAgeException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_overBoundRandomAge_ok() {
        random = new Random();
        int overBoundRandomAge = random.nextInt(VALID_AGE_RANGE) + MIN_VALID_AGE;
        defaultUser.setAge(overBoundRandomAge);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_minimumLoginLength_ok() {
        defaultUser.setLogin(SIX_CHARS_STRING);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundLoginLength_notOk() {
        defaultUser.setLogin(FIVE_CHARS_STRING);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        defaultUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidLoginException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_minimumPasswordLength_ok() {
        defaultUser.setPassword(SIX_CHARS_STRING);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
    }

    @Test
    void register_underBoundPasswordLength_notOk() {
        defaultUser.setPassword(FIVE_CHARS_STRING);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        defaultUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidPasswordException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidArgumentException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_twoEqualUsers_notOk() {
        registrationService.register(defaultUser);
        assertThrows(UserAlreadyExistException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_threeDifferentValidUsers_ok() {
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
