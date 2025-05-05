package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Constant;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserAgeException;
import core.basesyntax.exception.InvalidUserLoginException;
import core.basesyntax.exception.InvalidUserPasswordException;
import core.basesyntax.exception.UserAlreadyExistsException;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser;

    @BeforeEach
    public void each() {
        Storage.people.clear();
        validUser = new User();
        validUser.setLogin(Constant.VALID_LOGIN);
        validUser.setAge(Constant.VALID_AGE);
        validUser.setPassword(Constant.VALID_PASSWORD);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(validUser, registrationService.register(validUser));

        User validUser2 = new User();
        validUser2.setLogin(Constant.VALID_LOGIN2);
        validUser2.setAge(Constant.VALID_AGE2);
        validUser2.setPassword(Constant.VALID_PASSWORD2);
        assertEquals(validUser2, registrationService.register(validUser2));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class,
                () -> registrationService.register(null)
        );
    }

    @Test
    void register_existUser_notOk() {
        Storage.people.add(validUser);
        UserAlreadyExistsException existsException = assertThrows(
                UserAlreadyExistsException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals("User " + validUser.getLogin() + " already exists",
                existsException.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        InvalidUserLoginException loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid login, must be at least " + Constant.MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin(Constant.INVALID_LOGIN);
        InvalidUserLoginException loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid login, must be at least " + Constant.MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );

        validUser.setLogin(Constant.INVALID_LOGIN2);
        loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid login, must be at least " + Constant.MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid age, should be over " + Constant.MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_childAge_notOk() {
        validUser.setAge(Constant.INVALID_AGE);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid age, should be over " + Constant.MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_negativeAge_notOk() {
        validUser.setAge(Constant.INVALID_AGE2);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid age, should be over " + Constant.MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        InvalidUserPasswordException passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid password, must be at least "
                        + Constant.MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword(Constant.INVALID_PASSWORD);
        InvalidUserPasswordException passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid password, must be at least "
                        + Constant.MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );

        validUser.setPassword(Constant.INVALID_PASSWORD2);
        passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid password, must be at least "
                        + Constant.MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );

        validUser.setPassword(Constant.INVALID_PASSWORD3);
        passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(validUser)
        );
        assertEquals(
                "Invalid password, must be at least "
                        + Constant.MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );
    }
}
