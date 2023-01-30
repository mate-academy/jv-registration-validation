package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String login = "Spider_Man";
    private static final String password = "qwerty123";
    private static final int age = 21;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
    }

    @Test
    void register_UserAlreadyExist_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPassword_notOk() {
        user.setPassword("1234");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userYoungAge_notOk() {
        user.setAge(14);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userNegativeAge_notOk() {
        user.setAge(-2);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeOver18_ok() {
        user.setAge(40);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
