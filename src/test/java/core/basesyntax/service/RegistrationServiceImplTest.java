package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("defaultLogin");
        testUser.setPassword("defaultPassword");
        testUser.setAge(18);
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        testUser.setLogin("NullAgeUser");
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        testUser.setLogin("NullPassUser");
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_withExistingLogin_NotOk() {
        final String login = "UniLogin";
        testUser.setLogin(login);
        registrationService.register(testUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeLessThen18_NotOk() {
        testUser.setLogin("17YOLogin");
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        testUser.setLogin("NegativeAgeLogin");
        testUser.setAge(-5433);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_userAge18_Ok() {
        testUser.setLogin("18YOLogin");
        testUser.setAge(18);
        int expected = 18;
        assertEquals(expected, registrationService.register(testUser).getAge());
    }

    @Test
    void register_userAgeBigger18_Ok() {
        testUser.setLogin("44YOLogin");
        testUser.setAge(44);
        int expected = 44;
        assertEquals(expected, registrationService.register(testUser).getAge());
    }
}
