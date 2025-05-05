package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static String okLogin;
    private static String okPassword;
    private static int okAge;
    private RegistrationService registrationService;

    @BeforeAll
    static void setup() {
        // Removed okLoginTwo and okLoginThree
        // That fields were used just two times, specific to two test cases
        okLogin = "panamPalmer";
        okPassword = "hello123world";
        okAge = 21;
    }

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userWithExistedLogin_notOk() {
        User userToRegister = new User(okLogin, okPassword, okAge);
        User userToRegisterWithSameLogin = new User(okLogin, "newSecretPassword123", 29);

        assertEquals(registrationService.register(userToRegister), userToRegister);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userToRegisterWithSameLogin);
        });
    }

    @Test
    void register_userWithTooShortLogin_notOk() {
        User userWithZeroSymbolsLogin = new User("", okPassword, okAge);
        User userWithFiveSymbolsLogin = new User("johny", okPassword, okAge);
        User userWithNullLogin = new User(null, okPassword, okAge);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithZeroSymbolsLogin);
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithFiveSymbolsLogin);
        });

        assertThrows(NullPointerException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void register_userWithTooShortPassword_notOk() {
        User userWithFiveSymbolsPassword = new User(okLogin, "qwert", okAge);
        User userWithZeroSymbolsPassword = new User("jackieWells", "", okAge);
        User userWithNullPassword = new User("johnySilverhand", null, okAge);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithZeroSymbolsPassword);
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithFiveSymbolsPassword);
        });

        assertThrows(NullPointerException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void register_userWithTooYoungUser_notOk() {
        User userWithZeroAge = new User(okLogin, okPassword, 0);
        User userWithNegativeAge = new User("jackieWells", okPassword, -100);
        User userWithEdgeCaseBadAge = new User("johnySilverhand", okPassword, 17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithZeroAge);
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNegativeAge);
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithEdgeCaseBadAge);
        });
    }

    @Test
    void register_userWithRightParams_ok() {
        User userToRegister = new User(okLogin, okPassword, okAge);
        assertEquals(registrationService.register(userToRegister), userToRegister);
    }

    @Test
    void register_userWithRightButEdgeParams_ok() {
        User user = new User("johnny", "123456", 18);
        assertEquals(registrationService.register(user), user);
    }

    @AfterEach
    void clenUp() {
        Storage.people.clear();
    }
}
