package core.basesyntax.service;
/*
    Null throws CUSTOM exception;
    User's age/login/password not null;
    18 <= age >= 140;
    4 <= login.length() >= 16;
    6 <= password.l() >=16;
    User must have new login;
    Correct user test;
    Login must fit word pattern [a-zA-Z_0-9];
    Login must not have duplicates regardless to upperCase;
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INPUT_EXCEPTION_MESSAGE
            = "must throw InvalidUserInputException if input is invalid";
    private static final String USER_RETURN_EXCEPTION_MESSAGE =
            "Registration should return valid user";
    private static final String DUPLICATED_LOGIN_MESSAGE =
            "Duplicated login should throw InvalidUserInputException";
    private static final String CORRECT_LOGIN = "Ilko";
    private static final String CORRECT_LOGIN2 = "GOOD";
    private static final String SAME_CORRECT_LOGIN_UPPERCASE = "ILKO";
    private static final String TOO_SHORT_LOGIN = "o";
    private static final String TOO_LONG_LOGIN = "Ilko12345678901234567890";
    private static final String CORRECT_PASSWORD = "12345678";
    private static final String TOO_SHORT_PASSWORD = "135";
    private static final String TOO_LONG_PASSWORD = "1234567890QWERTY_qwerty";
    private static final Integer CORRECT_AGE = 20;
    private static final Integer SMALL_AGE = 17;
    private static final Integer UNREAL_AGE = 150;
    private final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private User user = new User();

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void nullUser_throwsException_ok() {
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(null));
    }

    @Test
    void nullParams_throwsException_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);

        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(null);
        user.setAge(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);

        user = new User();
        user.setLogin(null);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void incorrectAge_throwsException_ok() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(SMALL_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
        user.setAge(UNREAL_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void incorrect_Login_throwsException_ok() {
        user.setLogin(TOO_SHORT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
        user.setLogin(TOO_LONG_LOGIN);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void incorrectPassword_throwsException_ok() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(TOO_SHORT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
        user.setLogin(TOO_LONG_PASSWORD);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void registerUser_ok() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertEquals(user, registrationService.register(user),
                USER_RETURN_EXCEPTION_MESSAGE);
        user = new User();
        user.setLogin(CORRECT_LOGIN2);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertEquals(user, registrationService.register(user),
                USER_RETURN_EXCEPTION_MESSAGE);
    }

    @Test
    void userAlreadyExists_throwsException_ok() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        registrationService.register(user);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), DUPLICATED_LOGIN_MESSAGE);
        user = new User();
        user.setLogin(SAME_CORRECT_LOGIN_UPPERCASE);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), DUPLICATED_LOGIN_MESSAGE);
    }

    @Test
    void illegalCharactersLogin_throwsException_ok() {
        user.setLogin("Ilko123#12");
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }
}
