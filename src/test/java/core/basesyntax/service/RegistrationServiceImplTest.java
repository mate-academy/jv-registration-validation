package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class RegistrationServiceImplTest {
    private static final String STRING_123 = "123";
    private static final String STRING_12345 = "12345";
    private static final String STRING_12345678 = "12345678";
    private static final String CORRECT_LOGIN = "123456";
    private static final String CORRECT_PASSWORD = "654321";
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(RegistrationServiceImpl.MIN_AGE);
    }

    @Test
    @DisplayName("user equals null not Ok")
    void register_nullUser_notOk() {
        assertThrows(RegistrationDataException.class, () -> registrationService.register(null));
    }

    @Test
    @DisplayName("user already exist not Ok")
    void register_alreadyExistLogin_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with null value not Ok")
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length 0 not Ok")
    void register_emptyUserLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length shorter "
            + RegistrationServiceImpl.MIN_LOGIN_LENGTH + " not Ok")
    void register_shortLogin_notOk() {
        user.setLogin(STRING_123);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
        user.setLogin(STRING_12345);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length equals or greater "
            + RegistrationServiceImpl.MIN_LOGIN_LENGTH + " Ok")
    void register_longLogin_Ok() {
        user.setLogin(CORRECT_LOGIN);
        User actual = registrationService.register(user);
        assertEquals(RegistrationServiceImpl.MIN_LOGIN_LENGTH, actual.getLogin().length(),
                "login length must be equals " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
        user.setLogin(STRING_12345678);
        Storage.people.clear();
        actual = registrationService.register(user);
        assertTrue(actual.getLogin().length() > RegistrationServiceImpl.MIN_LOGIN_LENGTH,
                "login length must be > " + RegistrationServiceImpl.MIN_LOGIN_LENGTH);
    }

    @Test
    @DisplayName("password with null value not Ok")
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length 0 not Ok")
    void register_emptyUserPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length shorter "
            + RegistrationServiceImpl.MIN_PASSWORD_LENGTH + " not Ok")
    void register_shortPassword_notOk() {
        user.setPassword(STRING_123);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
        user.setPassword(STRING_12345);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length equals or greater "
            + RegistrationServiceImpl.MIN_PASSWORD_LENGTH + " Ok")
    void register_longPassword_Ok() {
        user.setPassword(CORRECT_PASSWORD);
        User actual = registrationService.register(user);
        assertEquals(RegistrationServiceImpl.MIN_PASSWORD_LENGTH, actual.getPassword().length(),
                "password length must be equals " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
        user.setPassword(STRING_12345678);
        Storage.people.clear();
        actual = registrationService.register(user);
        assertTrue(actual.getPassword().length() > RegistrationServiceImpl.MIN_PASSWORD_LENGTH,
                "password length must be > " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("age with null value not Ok")
    void register_nullUserAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("age less " + RegistrationServiceImpl.MIN_AGE + " not Ok")
    void register_lessThanMinimalAge_notOk() {
        user.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("age with negative value not Ok")
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(RegistrationDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("age equals " + RegistrationServiceImpl.MIN_AGE + " Ok")
    void register_equalsMinimalAge_Ok() {
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        final User registeredUser = registrationService.register(user);
        assertEquals(user.getAge().intValue(), registeredUser.getAge().intValue());
    }

    @Test
    @DisplayName("age greater " + RegistrationServiceImpl.MIN_AGE + " Ok")
    void register_greaterThanMinimalAge_Ok() {
        user.setAge(RegistrationServiceImpl.MIN_AGE + 1);
        final User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge(), "ages must be equals");
    }
}
