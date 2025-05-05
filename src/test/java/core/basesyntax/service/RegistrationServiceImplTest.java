package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.IllegalUserDataExeption;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "ValidLogin";
    private static final String DEFAULT_PASSWORD = "ValidPassword";
    private static final int DEFAULT_AGE = 20;
    private static final long DEFAULT_ID = 1L;
    private static final String NOT_VALID_LOGIN = "noval";
    private static final String NOT_VALID_PASSWORD = "nopas";
    private static final int NOT_VALID_AGE = 17;
    private static RegistrationService registrationService;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
    }

    @BeforeEach
    void setUp() {
        validUser.setLogin(DEFAULT_LOGIN);
        validUser.setPassword(DEFAULT_PASSWORD);
        validUser.setAge(DEFAULT_AGE);
        validUser.setId(DEFAULT_ID);
    }

    @Test
    void register_validUser_Ok() {
        Storage.people.clear();
        User registredUser = registrationService.register(validUser);
        assertEquals(validUser, registredUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidLogin_notOk() {
        validUser.setLogin(NOT_VALID_LOGIN);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidPassword_notOk() {
        validUser.setPassword(NOT_VALID_PASSWORD);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void resister_notValidAge_notOk() {
        validUser.setAge(NOT_VALID_AGE);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_duplicateLogin_notOk() {
        Storage.people.clear();
        registrationService.register(validUser);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }
}
