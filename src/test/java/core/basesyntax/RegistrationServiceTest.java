package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Feel free to remove this class and create your own.
 */
@TestMethodOrder(OrderAnnotation.class)
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
    @Order(1)
    void register_login_Ok() {
        User expected = getUser(id, loginDefault, passDefault, ageDefault);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "register method return value");
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "added to storage user");
    }

    @Test
    @DisplayName("Null login handling")
    @Order(2)
    void register_loginNull_NotOk() {
        User user = getUser(id, loginNull, passDefault, ageDefault);
        assertAll(
                ()->assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "login couldn't be null"),
                ()->assertTrue(Storage.people.isEmpty())
        );
    }

    @Test
    @DisplayName("Existing login handling")
    @Order(3)
    void register_loginExist_NotOk() {
        User expected = getUser(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected);
        User sameLoginUser = getUser(1L, loginDefault, passDefault, ageDefault);
        assertAll(
                ()->assertThrows(RuntimeException.class,
                ()->registrationService.register(sameLoginUser), "login = null"),

                ()->assertEquals(1, Storage.people.size(), "Storage size"),
                ()->assertEquals(expected, Storage.people.get(0), "Added to Storage user"));
    }

    @Test
    @DisplayName("Different users handling")
    @Order(4)
    void register_loginDiff_Ok() {
        String loginAnother = new StringBuilder(loginDefault).reverse().toString();
        User expected_0 = getUser(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected_0);
        User expected_1 = getUser(1L, loginAnother, passDefault, ageDefault);
        User actual_1 = registrationService.register(expected_1);
        assertEquals(expected_1, actual_1, "register method return value");
        assertEquals(2, Storage.people.size(), "Storage size");
        assertEquals(expected_0, Storage.people.get(0), "Storage user 0");
        assertEquals(expected_1, Storage.people.get(1), "Storage user 1");
    }

    @Test
    @DisplayName("Null password handling")
    @Order(5)
    void register_passwordNull_NotOk() {
        User user = getUser(id, loginDefault, passNull, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "password couldn't be null");
    }

    @Test
    @DisplayName("Short password handling")
    @Order(6)
    void register_passwordShort_NotOk() {
        User user = getUser(id, loginDefault, passShort, ageDefault);
        assertAll(
                ()->assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "password couldn't be null"),
                ()->assertTrue(Storage.people.isEmpty())
        );
    }

    @Test
    @DisplayName("Null age handling")
    @Order(7)
    void register_ageNull_NotOk() {
        User user = getUser(id, loginDefault, passDefault, ageNull);
        assertAll(
                ()->assertThrows(RuntimeException.class, ()->registrationService.register(user),
                        "age couldn't be null"),
                ()->assertTrue(Storage.people.isEmpty())
        );
    }

    @Test
    @DisplayName("Small age handling")
    @Order(8)
    void register_ageYoung_NotOk() {
        User user = getUser(id, loginDefault, passDefault, ageYoung);
        assertAll(
                ()->assertThrows(RuntimeException.class, ()->registrationService.register(user),
                        "age couldn't be less then " + ageBarely),
                ()->assertTrue(Storage.people.isEmpty())
        );
    }

    @Test
    @DisplayName("Integer.MIN age handling")
    @Order(9)
    void register_ageMin_NotOk() {
        User user = getUser(id, loginDefault, passDefault, ageMin);
        assertAll(
                ()->assertThrows(RuntimeException.class, ()->registrationService.register(user),
                        "age couldn't be less then " + ageBarely),
                ()->assertTrue(Storage.people.isEmpty())
        );
    }

    @Test
    @DisplayName("Integer.MAX age handling")
    @Order(10)
    void register_ageMax_Ok() {
        User expected = getUser(id, loginDefault, passDefault, ageMax);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "Storage user 0");
    }

    @Test
    @DisplayName("Barely legal age handling")
    @Order(11)
    void register_ageBarely_Ok() {
        User expected = getUser(id, loginDefault, passDefault, ageBarely);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "Storage user 0");
    }

    private static User getUser(Long id, String login, String password, Integer age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
