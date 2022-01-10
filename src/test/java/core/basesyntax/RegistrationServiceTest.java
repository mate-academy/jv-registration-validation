package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private String loginNull;
    private String loginEmpty = "";
    private String loginDefault = "login";
    private String passNull;
    private String passShort = "short";
    private String passDefault = "password";
    private Integer ageNull;
    private Integer ageSmall = 17;
    private Integer ageBarely = 18;
    private Integer ageMax = Integer.MAX_VALUE;
    private Integer ageDefault = 25;
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
        User user = getUser(id, loginNull, passDefault, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user), "login = null");
    }

    @Test
    @DisplayName("Null login handling")
    @Order(2)
    void loginNull_NotOk() {
        User user = getUser(id, loginNull, passDefault, ageDefault);
        assertThrows(RuntimeException.class, ()->registrationService.register(user), "login = null");
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
        User expected = getUser(id, loginDefault, passDefault, ageDefault);
        registrationService.register(expected);
        User sameLoginUser = getUser(1L, loginDefault, passDefault, ageDefault);
        assertAll(
                ()->assertThrows(RuntimeException.class,
                        ()->registrationService.register(sameLoginUser), "login = null"),

                ()->assertEquals(1, Storage.people.size(), "Storage size"),
                ()->assertEquals(expected, Storage.people.get(0), "Added to Storage user"));
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
