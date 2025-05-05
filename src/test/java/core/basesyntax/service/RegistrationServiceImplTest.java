package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        validUser.setLogin("jcbgxer@gmail.com");
        validUser.setPassword("123456");
        validUser.setAge(22);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registeredValidUser_Ok() {
        assertDoesNotThrow(() -> registrationService.register(validUser));
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
    void registeredShortLogin_NotOk() {
        validUser.setLogin("Tom");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredShortPassword_NotOk() {
        validUser.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredYoungUser_NotOk() {
        validUser.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredUserCopy_NotOk() {
        registrationService.register(validUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void registeredLoginWithoutSymbol_NotOk() {
        validUser.setLogin("jcbgxergmail.com");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }
}
