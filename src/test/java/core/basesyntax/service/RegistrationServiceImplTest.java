package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static User validUser;
    private static final String EMPTY_STRING = "";
    private static final String VALID_PASSWORD = "password";
    private static final String MIN_VALID_PASSWORD = "pass12";
    private static final String VALID_LOGIN = "user_login";
    private static final String MIN_VALID_LOGIN = "user12";
    private static final int MIN_AGE = 18;
    private static final int VALID_AGE = 25;
    private static final String INVALID_PASSWORD_OR_LOGIN1 = "p";
    private static final String INVALID_PASSWORD_OR_LOGIN2 = "pa";
    private static final String INVALID_PASSWORD_OR_LOGIN3 = "pas";
    private static final String INVALID_PASSWORD_OR_LOGIN4 = "pass";
    private static final String INVALID_PASSWORD_OR_LOGIN5 = "users";
    private static final String ERROR_MESSAGE_TEMPLATE = "Storage must have been rejected"
            + " adding user with ";

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void register_emptyUser_notOk() {
        assertThrows(InvalidUserData.class, () -> service.register(new User()));
    }

    @Test
    void register_loginNull_notOk() {
        validUser.setLogin(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "login ";

        validUser.setLogin(EMPTY_STRING);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + EMPTY_STRING);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN1);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN1);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN2);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN2);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN3);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN3);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN4);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN4);

        validUser.setLogin(INVALID_PASSWORD_OR_LOGIN5);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN5);
    }

    @Test
    void register_minValidLogin_Ok() {
        validUser.setLogin(MIN_VALID_LOGIN);
        String actual = service.register(validUser).getLogin();
        String expected = MIN_VALID_LOGIN;
        assertEquals(expected, actual);
    }

    @Test
    void register_validLogin_Ok() {
        validUser.setLogin(VALID_LOGIN);
        String actual = service.register(validUser).getLogin();
        String expected = VALID_LOGIN;
        assertEquals(expected, actual);
    }

    @Test
    void register_passwordNull_notOk() {
        validUser.setPassword(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "password ";

        validUser.setPassword(EMPTY_STRING);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + EMPTY_STRING);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN1);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN1);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN2);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN2);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN3);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN3);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN4);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN4);

        validUser.setPassword(INVALID_PASSWORD_OR_LOGIN5);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + INVALID_PASSWORD_OR_LOGIN5);
    }

    @Test
    void register_minValidPasswordOk() {
        validUser.setPassword(MIN_VALID_PASSWORD);
        String actual = service.register(validUser).getPassword();
        String expected = MIN_VALID_PASSWORD;
        assertEquals(expected, actual);
    }

    @Test
    void register_validPassword_Ok() {
        validUser.setPassword(VALID_PASSWORD);
        String actual = service.register(validUser).getPassword();
        String expected = VALID_PASSWORD;
        assertEquals(expected, actual);
    }

    @Test
    void register_ageNull_notOk() {
        validUser.setAge(null);
        assertThrows(InvalidUserData.class, () -> service.register(validUser));
    }

    @Test
    void register_invalidAge_notOk() {
        String errorDescription = ERROR_MESSAGE_TEMPLATE + "age ";
        final int negativeAge = -1;
        final int ageUnder18 = 17;

        validUser.setAge(negativeAge);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + negativeAge + ". Age can't be less than 0");

        validUser.setAge(ageUnder18);
        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                errorDescription + ageUnder18 + ". Age must be at least 18 years old");

    }

    @Test
    void register_minValidAgeOk() {
        validUser.setAge(MIN_AGE);
        int actual = service.register(validUser).getAge();
        int expected = MIN_AGE;
        assertEquals(expected, actual);
    }

    @Test
    void register_validAge_Ok() {
        validUser.setAge(VALID_AGE);
        int actual = service.register(validUser).getAge();
        int expected = VALID_AGE;
        assertEquals(expected, actual);
    }

    @Test
    void register_missingLogin_notOk() {
        validUser.setLogin(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " login");
    }

    @Test
    void register_noPassword_notOk() {
        validUser.setPassword(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " password");
    }

    @Test
    void register_noAge_notOk() {
        validUser.setAge(null);

        assertThrows(InvalidUserData.class, () -> service.register(validUser),
                ERROR_MESSAGE_TEMPLATE + null + " age");
    }

    @Test
    void register_userWithSameLogin_notOk() {
        Storage.people.add(validUser);

        assertThrows(UserAlreadyExist.class, () -> service.register(validUser));
    }

    @Test
    void register_validUser_ok() {
        User newUser = service.register(validUser);
        assertEquals(validUser, newUser,
                "Added user and user in storage are not equal");
    }
}
