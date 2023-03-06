package core.basesyntax.service;
/*
    RULES:
        Null throws CUSTOM unchecked exception;
        User's age/login/password can't be null;
        Age must be in range 18 - 140 inclusively;
        Login length must be in range 4 - 16 inclusively;
        Password length must be in range 6 - 16 inclusively;
        User must have new unique login;
        Correct users must be added;
        Login must fit word pattern [a-zA-Z_0-9];
        Login must not have duplicates regardless to upperCase;
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INPUT_EXCEPTION_MESSAGE
            = "must throw InvalidUserInputException if input is invalid";
    private static final String USER_RETURN_EXCEPTION_MESSAGE =
            "Registration should return valid registered user";
    private static final String DUPLICATED_LOGIN_MESSAGE =
            "Duplicated login should throw InvalidUserInputException";
    private static final String CORRECT_LOGIN = "Ilko";
    private static final String CORRECT_PASSWORD = "12345678";
    private static final Integer CORRECT_AGE = 18;

    private final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(null), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_smallAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_highAge_notOk() {
        user.setAge(160);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_minusAge_notOk() {
        user.setAge(-50);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("o");
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_longLogin_notOk() {
        user.setLogin("Ilko12345678901234567890");
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_longPassword_notOk() {
        user.setPassword("1234567890QWERTY_qwerty");
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }

    @Test
    void register_usersInSequence_ok() {
        assertEquals(1, (long) registrationService.register(user).getId(),
                USER_RETURN_EXCEPTION_MESSAGE);

        user = new User();
        user.setLogin("GOOD");
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertEquals(2, (long) registrationService.register(user).getId(),
                USER_RETURN_EXCEPTION_MESSAGE);

        user = new User();
        user.setLogin("User_3");
        user.setPassword("Some_Password");
        user.setAge(55);
        assertEquals(3, (long) registrationService.register(user).getId(),
                USER_RETURN_EXCEPTION_MESSAGE);
        assertEquals(3, Storage.people.size(),
                "storage size grows incorrectly");
    }

    @Test
    void register_existingUser_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), DUPLICATED_LOGIN_MESSAGE);
        user = new User();
        user.setLogin(CORRECT_LOGIN.toUpperCase());
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), DUPLICATED_LOGIN_MESSAGE);
    }

    @Test
    void register_illegalCharactersLogin_notOk() {
        user.setLogin("Ilko123#12");
        assertThrows(InvalidUserInputException.class,
                () -> registrationService.register(user), INPUT_EXCEPTION_MESSAGE);
    }
}
