package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INCORRECT_AGE = 15;
    private static final int DEFAULT_AGE = 18;
    private static final String INCORRECT_PASSWORD = "afdsf";
    private static final String DEFAULT_PASSWORD = "abca4567";
    private static final String DEFAULT_LOGIN = "matestudent@gmail.com";
    private static final String INCORRECT_LOGIN = "abas";
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void registerUser_null_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User shouldn't be null");
    }

    @Test
    void registerUser_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_lengthPassword_NotOk() {
        user.setPassword(INCORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_lengthLogin_NotOk() {
        user.setLogin(INCORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
    
    @Test
    void registerUser_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_amountAge_NotOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLengthValid_OK() {
        user.setLogin("newlogin@example.com"); // Use a different login
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        int expected = 8;
        int actual = registrationService.register(user).getPassword().length();
        assertEquals(expected, actual);
    }

    @Test
    void register_loginalreadytaken_NotOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
