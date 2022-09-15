package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_IS_OK = "Vasyl";
    private static final String PASSWORD_IS_OK = "VasylM";
    private static final int AGE_IS_OK = 18;
    private static RegistrationServiceImpl registrationService;

    private User userAppropriate = new User(LOGIN_IS_OK ,PASSWORD_IS_OK ,AGE_IS_OK);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullLogin_NotOk() {
        userAppropriate.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAppropriate));
    }

    @Test
    void register_validUser_Ok() {
        User actualUser = new User("Moshun","123456", 20);
        User exceptUser = registrationService.register(actualUser);
        assertEquals(exceptUser,exceptUser);
    }

    @Test
    void register_lessAge() {
        userAppropriate.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAppropriate));
    }

    @Test
    void register_shortPassword_notOk() {
        userAppropriate.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(userAppropriate));
    }

    @Test
    void register_existsLogin_notOk() {
        User actual = registrationService.register(userAppropriate);
        assertEquals(actual,userAppropriate);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAppropriate));
    }
}
