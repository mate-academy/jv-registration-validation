package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_LOGIN = "asdas";
    private static final String INVALID_PASSWORD = "12314";
    private static final int INVALID_AGE = 16;
    private static RegistrationServiceImpl registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User("ValidUser", "123412346", 18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_emailAlreadyExists_notOk() {
        Storage.people.add(validUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_loginLessThanMinimum_notOk() {
        validUser.setLogin(INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_passwordLessThanMinimum_notOk() {
        validUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_ageLessThanMinimum_notOk() {
        validUser.setAge(INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }
}
