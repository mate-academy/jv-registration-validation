package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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

    @Test
    void register_validUser_ok() {
        validUser.setLogin("validUser");
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_emailAlreadyExists_notOk() {
        Storage.people.add(validUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_loginLessThanSixChars_notOk() {
        validUser.setLogin(INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_passwordLessThanSixChars_notOk() {
        validUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
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
