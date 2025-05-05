package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User validUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin("bodje343r@gmail.com");
        validUser.setPassword("121212");
        validUser.setAge(27);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUser_withValidData_Ok() {
        User expectedUser = validUser;
        User actualUser = registrationService.register(validUser);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void registeredNullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredNullLoginUser_NotOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredNullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registerUser_withShortLogin_NotOk() {
        validUser.setLogin("Tom");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));

        validUser.setLogin("John@gmail.com");
        assertDoesNotThrow(() -> registrationService.register(validUser),
                "Expected successful registration for minimum valid login length");
    }

    @Test
    void registerUser_withInvalidPassword_NotOk() {
        // Short password
        validUser.setPassword("sho!");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));

        // Edge case: Minimum valid length (assuming it's 6 for this example)
        validUser.setPassword("Valid123");
        assertDoesNotThrow(() -> registrationService.register(validUser));

        // Password without numbers
        validUser.setPassword("NoNumbers!");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));

        // Password without special characters
        validUser.setPassword("NoSpecialCharacters123");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registerUser_withInvalidAge_NotOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Expected ValidationException for age below minimum");

        // Edge case: Minimum valid age
        validUser.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(validUser),
                "Expected successful registration for minimum valid age");

        // Age above maximum (assuming it's 150)
        validUser.setAge(151);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser),
                "Expected ValidationException for age above maximum");
    }

    @Test
    void registerUser_withMaximumValidAge_Ok() {
        validUser.setAge(150);
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    void registeredUserCopy_NotOk() {
        registrationService.register(validUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredLoginWithoutSymbol_NotOk() {
        validUser.setLogin("fsdd456jhgmail.com");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }
}
