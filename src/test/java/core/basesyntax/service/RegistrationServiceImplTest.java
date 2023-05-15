package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int incorrectAge = 15;
    private static final int DefaultAge = 18;
    private static final String incorrectPassword = "afdsf";
    private static final String DefaultPassword = "abca4567";
    private static final String DefaultLogin = "username@gmail.com";
    private static final String incorrectLogin = "abas";
    private static RegistrationServiceImpl registrationService;
    private static final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user.setAge(DefaultAge);
        user.setLogin(DefaultLogin);
        user.setPassword(DefaultPassword);
    }

    @Test
    void checkisUser_Null() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User shouldn't be null");
    }

    @Test
    void checkisPassword_Null() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkisPasswordlength_NotOk() {
        user.setPassword(incorrectPassword);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkisLogin_Null() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkisLoginLength_NotOk() {
        user.setLogin(incorrectLogin);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    
    @Test
    void checkAge_Null() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void checkAge_notOk() {
        user.setAge(incorrectAge);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginalreadytaken_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
