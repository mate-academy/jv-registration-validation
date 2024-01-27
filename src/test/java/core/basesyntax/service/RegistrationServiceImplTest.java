package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private static String okLoginOne;
    private static String okLoginTwo;
    private static String okLoginThree;
    private static String okPassword;
    private static int okAge;
    private RegistrationService registrationService;

    @BeforeAll
    static void setup() {
        okLoginOne = "jackieWells";
        okLoginTwo = "panamPalmer";
        okLoginThree = "johnySilverhand";
        okPassword = "hello123world";
        okAge = 21;
    }

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userThatIsAlreadyRegistered_notOk() {
        User userToRegisterTwice = new User(okLoginTwo, okPassword, okAge);

        assertEquals(registrationService.register(userToRegisterTwice), userToRegisterTwice);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userToRegisterTwice);
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
        User userWithZeroSymbolsPassword = new User(okLoginOne, "", okAge);
        User userWithFiveSymbolsPassword = new User(okLoginTwo, "qwert", okAge);
        User userWithNullPassword = new User(okLoginThree, null, okAge);

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
        User userWithZeroAge = new User(okLoginOne, okPassword, 0);
        User userWithNegativeAge = new User(okLoginTwo, okPassword, -100);
        User userWithEdgeCaseBadAge = new User(okLoginThree, okPassword, 17);

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
        User userToRegisterOne = new User(okLoginOne, okPassword, okAge);
        User userToRegisterTwo = new User(okLoginTwo, okPassword, okAge);
        User userToRegisterThree = new User(okLoginThree, okPassword, okAge);

        assertEquals(registrationService.register(userToRegisterOne), userToRegisterOne);
        assertEquals(registrationService.register(userToRegisterTwo), userToRegisterTwo);
        assertEquals(registrationService.register(userToRegisterThree), userToRegisterThree);
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
