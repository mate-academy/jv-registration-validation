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
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    @DisplayName("user already exist not Ok")
    void register_alreadyExistUser_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with null value not Ok")
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length 0 not Ok")
    void register_0LengthUserLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length " + 3 + " not Ok")
    void register_3LengthUserLogin_notOk() {
        user.setLogin(STRING_123);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length shorter "
            + RegistrationServiceImpl.MIN_LOGIN_LENGTH + " not Ok")
    void register_shorterThanMinLengthLogin_notOk() {
        user.setLogin(STRING_12345);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("login with length equals "
            + RegistrationServiceImpl.MIN_LOGIN_LENGTH + " Ok")
    void register_equalsMinLengthLogin_Ok() {
        user.setLogin(CORRECT_LOGIN);
        final User actual = registrationService.register(user);
        assertEquals(RegistrationServiceImpl.MIN_LOGIN_LENGTH, actual.getLogin().length(),
                "login length must be equals " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("login with length greater "
            + RegistrationServiceImpl.MIN_LOGIN_LENGTH + " Ok")
    void register_longerThanMinLengthLogin_Ok() {
        user.setLogin(STRING_12345678);
        final User actual = registrationService.register(user);
        assertTrue(actual.getLogin().length() > RegistrationServiceImpl.MIN_LOGIN_LENGTH,
                "login length must be > " + RegistrationServiceImpl.MIN_LOGIN_LENGTH);
    }

    @Test
    @DisplayName("password with null value not Ok")
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length 0 not Ok")
    void register_0LengthUserPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length " + 3 + " not Ok")
    void register_3LengthUserPassword_notOk() {
        user.setPassword(STRING_123);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length shorter "
            + RegistrationServiceImpl.MIN_PASSWORD_LENGTH + " not Ok")
    void register_shorterThanMinLengthPassword_notOk() {
        user.setPassword(STRING_12345);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("password with length equals "
            + RegistrationServiceImpl.MIN_PASSWORD_LENGTH + " Ok")
    void register_equalsMinLengthPassword_Ok() {
        user.setPassword(CORRECT_PASSWORD);
        final User actual = registrationService.register(user);
        assertEquals(RegistrationServiceImpl.MIN_PASSWORD_LENGTH, actual.getPassword().length(),
                "password length must be equals " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("password with length greater "
            + RegistrationServiceImpl.MIN_PASSWORD_LENGTH + " Ok")
    void register_longerThanMinLengthPassword_Ok() {
        user.setPassword(STRING_12345678);
        final User actual = registrationService.register(user);
        assertTrue(actual.getPassword().length() > RegistrationServiceImpl.MIN_PASSWORD_LENGTH,
                "password length must be > " + RegistrationServiceImpl.MIN_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("age with null value not Ok")
    void register_nullUserAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("age less " + RegistrationServiceImpl.MIN_AGE + " not Ok")
    void register_lessThanMinimalAge_notOk() {
        user.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("age with negative value not Ok")
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
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
