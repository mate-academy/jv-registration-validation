package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    // Login must contain at least 6 symbols
    void register_UserThatIsAlreadyRegistered_NotOk() {
        User userToRegisterTwice = new User(okLoginTwo, okPassword, okAge);

        assertEquals(registrationService.register(userToRegisterTwice), userToRegisterTwice);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userToRegisterTwice);
        });

        Storage.people.remove(userToRegisterTwice);
    }

    @Test
    void register_UserWithTooShortLogin_NotOk() {
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

        Storage.people.remove(userWithZeroSymbolsLogin);
        Storage.people.remove(userWithFiveSymbolsLogin);
        Storage.people.remove(userWithNullLogin);
    }

    @Test
    void register_UserWithTooShortPassword_NotOk() {
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

        Storage.people.remove(userWithZeroSymbolsPassword);
        Storage.people.remove(userWithFiveSymbolsPassword);
        Storage.people.remove(userWithNullPassword);
    }

    @Test
    void register_UserWithTooYoungUser_NotOk() {
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

        Storage.people.remove(userWithZeroAge);
        Storage.people.remove(userWithNegativeAge);
        Storage.people.remove(userWithEdgeCaseBadAge);
    }

    @Test
    void register_UserWithRightParams_Ok() {
        User userToRegisterOne = new User(okLoginOne, okPassword, okAge);
        User userToRegisterTwo = new User(okLoginTwo, okPassword, okAge);
        User userToRegisterThree = new User(okLoginThree, okPassword, okAge);

        assertEquals(registrationService.register(userToRegisterOne), userToRegisterOne);
        assertEquals(registrationService.register(userToRegisterTwo), userToRegisterTwo);
        assertEquals(registrationService.register(userToRegisterThree), userToRegisterThree);

        Storage.people.remove(userToRegisterOne);
        Storage.people.remove(userToRegisterTwo);
        Storage.people.remove(userToRegisterThree);
    }

    @Test
    void register_UserWithRightButEdgeParams_Ok() {
        User user= new User("johnny", "123456", 18);
        assertEquals(registrationService.register(user), user);
        Storage.people.remove(user);
    }
}