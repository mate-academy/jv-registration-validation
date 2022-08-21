package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceTest {
    public static final String DEFAULT_LOGIN = "jesus";
    public static final String DEFAULT_PASSWORD = "christ";
    public static final int DEFAULT_AGE = 33;
    private static RegistrationService registrationService;
    private User expected_user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expected_user = new User();
        expected_user.setLogin(DEFAULT_LOGIN);
        expected_user.setPassword(DEFAULT_PASSWORD);
        expected_user.setAge(DEFAULT_AGE);
    }

    @Test
    void setDefaultUser_Ok() {
        User actual_user = registrationService.register(expected_user);
        assertEquals(expected_user, actual_user);
    }

    @Test
    void minimalLoginLength_Ok() {
        expected_user.setLogin("ab");
        User actual_user = registrationService.register(expected_user);
        assertEquals(expected_user, actual_user);
    }

    @Test
    void minimalValidAge_OK() {
        expected_user.setAge(18);
        User actual_user = registrationService.register(expected_user);
        assertEquals(expected_user, actual_user);
    }

    @Test
    void maximumValidAge_Ok() {
        expected_user.setAge(122);
        User actual_user = registrationService.register(expected_user);
        assertEquals(expected_user, actual_user);
    }

    @Test
    void twoDifferentUsers_Ok() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(30);
        registrationService.register(expected_user);
        registrationService.register(user);
        assertTrue(Storage.people.contains(expected_user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginNull_NotOk() {
        expected_user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void passwordNull_NotOk() {
        expected_user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void ageNull_NotOt() {
        expected_user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void emptyLogin_NotOk() {
        expected_user.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void shortLogin_NotOk() {
        expected_user.setLogin("a");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void shortPassword_NotOk() {
        expected_user.setLogin("five5");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void lessMinimalAge_NotOk() {
        expected_user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void moreMaximumAge_NotOk() {
        expected_user.setAge(123);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @Test
    void sameLogin_NotOk() {
        registrationService.register(expected_user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expected_user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
