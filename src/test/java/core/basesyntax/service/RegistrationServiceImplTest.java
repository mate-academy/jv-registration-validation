package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setId(1L);
        validUser.setAge(12);
        validUser.setLogin("login");
        validUser.setPassword("password");
    }

    @Test
    void register_validUser_Ok() {
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
        assertEquals(validUser.getAge(), registeredUser.getAge());
        assertEquals(validUser.getId(), registeredUser.getId());
        assertEquals(validUser.getPassword(), registeredUser.getPassword());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null), "User can't be null");

    }

    @Test
    void register_notAvailableLogin_notOk() {
        registrationService.register(validUser);
        User userWithNotAvailableLogin = new User();
        userWithNotAvailableLogin.setLogin("login");
        userWithNotAvailableLogin.setAge(22);
        userWithNotAvailableLogin.setId(2L);
        userWithNotAvailableLogin.setPassword("123qwe123");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNotAvailableLogin), "User login not available");
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = validUser;
        userWithNullLogin.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullLogin), "User login can't be null");
    }

    @Test
    void register_emptyLogin_notOk() {
        User userWithEmptyLogin = validUser;
        userWithEmptyLogin.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithEmptyLogin), "User login can't be empty");
    }

    @Test
    void register_tooShortLogin_notOk() {
        User userWithTooShortLogin = validUser;
        userWithTooShortLogin.setLogin("ll");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithTooShortLogin), "User login can't be shorter than 6");
    }

    @Test
    void register_onlyNumericLogin_notOk() {
        User userWithNumericLogin = validUser;
        userWithNumericLogin.setLogin("12345678");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNumericLogin), "User login can't be only numeric");
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = validUser;
        userWithNullPassword.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullPassword), "User password can't be null");
    }

    @Test
    void register_emptyPassword_notOk() {
        User userWithEmptyPassword = validUser;
        userWithEmptyPassword.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithEmptyPassword), "User password can't be empty");
    }

    @Test
    void register_onlyNumericPassword_notOk() {
        User userWithNumericPassword = validUser;
        userWithNumericPassword.setPassword("12345678");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNumericPassword), "User password can't be only numeric");
    }

    @Test
    void register_tooShortPassword_notOk() {
        User userWithTooShortPassword = validUser;
        userWithTooShortPassword.setPassword("ll12");
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithTooShortPassword), "User password can't be shorter than 6");
    }

    @Test
    void register_nullAge_notOk() {
        User userWithNullAge = validUser;
        userWithNullAge.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithNullAge), "User age can't be null");
    }

    @Test
    void register_tooYoungUser_notOk() {
        User tooYoungUser = validUser;
        tooYoungUser.setAge(-3);
        assertThrows(RuntimeException.class, () -> registrationService.register(tooYoungUser), "User must be at least 18 years old");
    }

    @Test
    void register_tooOldUser_notOk() {
        User tooOldUser = validUser;
        tooOldUser.setAge(300);
        assertThrows(RuntimeException.class, () -> registrationService.register(tooOldUser), "User can't be older than 100 years old");
    }
}