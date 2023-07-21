package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationServiceImpl;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
        validUser = User.of("valid_login", "valid_password", 32);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void validUserCase() {
        User expected = validUser;
        User actual = registrationServiceImpl.register(validUser);
        assertEquals(expected, actual);
    }

    @Test
    void validUserCase_withMinLengthLogin() {
        User newValidUser = User.of(validUser.getLogin().substring(0, LOGIN_MIN_LENGTH),
                validUser.getPassword(), validUser.getAge());
        User expected = newValidUser;
        User actual = registrationServiceImpl.register(newValidUser);
        assertEquals(expected, actual);
    }

    @Test
    void validUserCase_withMinAge() {
        User newValidUser = User.of(validUser.getLogin(), validUser.getPassword(), MIN_AGE);
        User expected = newValidUser;
        User actual = registrationServiceImpl.register(newValidUser);
        assertEquals(expected, actual);
    }

    @Test
    void validUserCase_withMinLengthPassword() {
        User newValidUser = User.of(validUser.getLogin(),
                validUser.getPassword().substring(0, PASSWORD_MIN_LENGTH), validUser.getAge());
        User expected = newValidUser;
        User actual = registrationServiceImpl.register(newValidUser);
        assertEquals(expected, actual);
    }

    @Test
    void userWithExistingLogin() {
        registrationServiceImpl.register(validUser);
        User existingLoginUser = User.of(validUser.getLogin(), "else_password", 19);
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(existingLoginUser);
        }, "This login already exist - " + validUser.getLogin());
    }

    @Test
    void nullUser() {
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(null);
        }, "User can`t be null");
    }

    @Test
    void userWithInvalidLogin() {
        String invalidLogin = "12345";
        User invalidLoginUser = User.of(invalidLogin, validUser.getPassword(),
                validUser.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(invalidLoginUser);
        }, "Login " + invalidLogin + " with length " + invalidLogin.length()
                + " is invalid by min length " + LOGIN_MIN_LENGTH);
        String nullLogin = null;
        User nullLoginUser = User.of(nullLogin, validUser.getPassword(), validUser.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(nullLoginUser);
        }, "Login can`t be null");
    }

    @Test
    void userWithInvalidPassword() {
        String invalidPassword = "12345";
        User invalidPasswordUser = User.of(validUser.getLogin(), invalidPassword,
                validUser.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(invalidPasswordUser);
        }, invalidPassword.length()
                + " password length is invalid for min length " + PASSWORD_MIN_LENGTH);
        String nullPassword = null;
        User nullPasswordUser = User.of(validUser.getLogin(), nullPassword, validUser.getAge());
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(nullPasswordUser);
        }, "Login can`t be null");
    }

    @Test
    void userWithInvalidAge() {
        Integer invalidLoverLimit = 17;
        User invalidAgeUserLover = User.of(validUser.getLogin(), validUser.getPassword(),
                invalidLoverLimit);
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(invalidAgeUserLover);
        }, invalidLoverLimit + " years is less then min age " + MIN_AGE);
        Integer negativeAge = -100;
        User negativeAgeUser = User.of(validUser.getLogin(), validUser.getPassword(),
                negativeAge);
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(negativeAgeUser);
        }, negativeAge + " years is less then min age " + MIN_AGE);
        Integer nullAge = null;
        User nullAgeUser = User.of(validUser.getLogin(), validUser.getPassword(), nullAge);
        assertThrows(UserRegistrationException.class, () -> {
            registrationServiceImpl.register(nullAgeUser);
        }, "Age can`t be null");
    }
}
