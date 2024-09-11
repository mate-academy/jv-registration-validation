package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private final User correctUser = new User("sixChr", "sixChr", 18);
    private final User moreThenEnough = new User("moreThenSix", "moreThenSix",23);
    private final User loginCopy = new User("sixChr", "blaBla", 1000);
    private final User nullUser = new User();
    private final User wrongLogin = new User("Short", "notShort", 19);
    private final User wrongPassword = new User("notShort", "Short", 19);
    private final User wrongAge = new User("notShort", "notShort", 17);
    private final User emptyLogin = new User(null, "notShort", 19);
    private final User emptyPassword = new User("notShort", null, 19);
    private final User emptyAge = new User("notShort", "notShort", null);
    private final User negativeAge = new User("notShort", "notShort", -7);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_correctUser_ok() {
        User actual = registrationService.register(correctUser);
        assertEquals(actual, correctUser);
    }

    @Test
    void register_moreThenEnough_ok() {
        User actual = registrationService.register(moreThenEnough);
        assertEquals(actual, moreThenEnough);
    }

    @Test
    void register_underAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(wrongAge);
        });
    }

    @Test
    void register_loginCopy_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(loginCopy);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(wrongLogin);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(wrongPassword);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(emptyLogin);
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(emptyAge);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(negativeAge);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(emptyPassword);
        });
    }
}
