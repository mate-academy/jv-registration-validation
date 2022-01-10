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

/**
 * Feel free to remove this class and create your own.
 */
@TestMethodOrder(OrderAnnotation.class)
public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private final String loginNull = null;
    private final String loginEmpty = "";
    private final String loginDefault = "login";
    private final String passNull = null;
    private final String passShort = "short";
    private final String passDefault = "password";
    private final Integer ageNull = null;
    private final Integer ageSmall = 17;
    private final Integer ageBarely = 18;
    private final Integer ageMax = Integer.MAX_VALUE;
    private final Integer ageMin = Integer.MIN_VALUE;
    private final Integer ageDefault = 25;
    long id = 0L; // there is no restrictions for id field


    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void init() {
        Storage.people.clear();
    }

    @Test
    @DisplayName("Correct login handling")
    @Order(1)
    void login_Ok() {
        User expected = getUser(id, loginDefault, passDefault, ageDefault);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "register method return value");
        assertEquals(1, Storage.people.size(), "Storage size");
        assertEquals(expected, Storage.people.get(0), "added to storage user");
    }

    @Test
    @DisplayName("Null login handling")
    @Order(2)
    void loginNull_NotOk() {
        User user = getUser(id, loginNull, passDefault, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "login couldn't be null");
    }

    @Test
    @DisplayName("Existing login handling")
    @Order(3)
    void loginExist_NotOk() {
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
    void loginDiff_Ok() {
        User expected_0 = getUser(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected_0);
        User expected_1 = getUser(1L, loginDefault, passDefault, ageDefault);
        User actual_1 = registrationService.register(expected_1);
        assertEquals(expected_1, actual_1, "register method return value");
        assertEquals(2, Storage.people.size(), "Storage size");
        assertEquals(expected_0, Storage.people.get(0), "Storage user 0");
        assertEquals(expected_1, Storage.people.get(1), "Storage user 1");
    }

    @Test
    @DisplayName("Null password handling")
    @Order(5)
    void passwordNull_NotOk() {
        User user = getUser(id, loginDefault, passNull, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "password couldn't be null");
    }

    @Test
    @DisplayName("Short password handling")
    @Order(6)
    void passwordShort_NotOk() {
        User user = getUser(id, loginDefault, passShort, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user),
                "password couldn't be null");
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
