package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Feel free to remove this class and create your own.
 */
@TestMethodOrder(Random.class)
public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final String loginNull = null;
    private static final String loginDefault = "login";
    private static final String passNull = null;
    private static final String passShort = "short";
    private static final String passDefault = "password";
    private static final Integer ageNull = null;
    private static final Integer ageYoung = 17;
    private static final Integer ageBarely = 18;
    private static final Integer ageMax = Integer.MAX_VALUE;
    private static final Integer ageMin = Integer.MIN_VALUE;
    private static final Integer ageDefault = 25;
    private static final long id = 0L; // there is no restrictions for id field

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    @DisplayName("Correct login handling")
    void register_login_Ok() {
        User expected = new User(id, loginDefault, passDefault, ageDefault);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "register method return value");
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "added to storage user");
    }

    @Test
    @DisplayName("Null login handling")
    void register_loginNull_NotOk() {
        User user = new User(id, loginNull, passDefault, ageDefault);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "login couldn't be null");
        assertTrue(Storage.people.isEmpty(), "Storage is not empty");
    }

    @Test
    @DisplayName("Existing login handling")
    void register_loginExist_NotOk() {
        User expected = new User(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected);
        User sameLoginUser = new User(1L, loginDefault, passDefault, ageDefault);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser),
                "login = null");
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "Added to Storage user");
    }

    @Test
    @DisplayName("Different users handling")
    void register_loginDiff_Ok() {
        String loginAnother = new StringBuilder(loginDefault).reverse().toString();
        User expected0 = new User(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected0);
        User expected1 = new User(1L, loginAnother, passDefault, ageDefault);
        User actual1 = registrationService.register(expected1);
        assertEquals(expected1, actual1, "register method return value");
        assertEquals(2, Storage.people.size(), "Storage size");
        assertEquals(expected0, Storage.people.get(0), "Storage user 0");
        assertEquals(expected1, Storage.people.get(1), "Storage user 1");
    }

    @Test
    @DisplayName("Null password handling")
    void register_passwordNull_NotOk() {
        User user = new User(id, loginDefault, passNull, ageDefault);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "password couldn't be null");
    }

    @Test
    @DisplayName("Short password handling")
    void register_passwordShort_NotOk() {
        User user = new User(id, loginDefault, passShort, ageDefault);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "password couldn't be null");
        assertTrue(Storage.people.isEmpty(), "Storage is not empty");
    }

    @Test
    @DisplayName("Null age handling")
    void register_ageNull_NotOk() {
        User user = new User(id, loginDefault, passDefault, ageNull);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                    "age couldn't be null");
        assertTrue(Storage.people.isEmpty(), "Storage is not empty");
    }

    @Test
    @DisplayName("Small age handling")
    void register_ageYoung_NotOk() {
        User user = new User(id, loginDefault, passDefault, ageYoung);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "age couldn't be less then " + ageBarely);
        assertTrue(Storage.people.isEmpty(), "Storage is not empty");
    }

    @Test
    @DisplayName("Integer.MIN age handling")
    void register_ageMin_NotOk() {
        User user = new User(id, loginDefault, passDefault, ageMin);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "age couldn't be less then " + ageBarely);
        assertTrue(Storage.people.isEmpty(), "Storage is not empty");
    }

    @Test
    @DisplayName("Integer.MAX age handling")
    void register_ageMax_Ok() {
        User expected = new User(id, loginDefault, passDefault, ageMax);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "Storage user 0");
    }

    @Test
    @DisplayName("Barely legal age handling")
    void register_ageBarely_Ok() {
        User expected = new User(id, loginDefault, passDefault, ageBarely);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "Storage user 0");
    }
}
