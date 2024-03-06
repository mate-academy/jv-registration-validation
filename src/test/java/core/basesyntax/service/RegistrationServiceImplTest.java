package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid login";
    private static final String VALID_PASSWORD = "valid pass";
    private static final Integer VALID_AGE = 20;
    private static final String EXCEPTION_MUST_BE_THROWN = "InvalidUserException must be thrown";
    private static final String STRING_5_LETTERS = "short";
    public static final String ILLEGAL_AGE = "Illegal age: ";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Storage.people.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(expectedUser, user, "User must be in storage but is not");

        User secondUser = new User();
        secondUser.setLogin("123456");
        secondUser.setPassword("123456");
        secondUser.setAge(18);
        registrationService.register(secondUser);
        expectedUser = storageDao.get(secondUser.getLogin());
        assertEquals(expectedUser, secondUser, "User must be in storage but is not");

        User thirdUser = new User();
        thirdUser.setLogin("gld9j44j;l 94ae");
        thirdUser.setPassword("fa 3w3p[pi03jp;at'safp-a0e");
        thirdUser.setAge(120);
        registrationService.register(thirdUser);
        expectedUser = storageDao.get(thirdUser.getLogin());
        assertEquals(expectedUser, thirdUser, "User must be in storage but is not");
    }

    @Test
    void register_loginIsTaken_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Login " + user.getLogin() + "is already taken",
                invalidUserException.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Login is null", invalidUserException.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin(STRING_5_LETTERS);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Login is too short, use at least 6 characters.",
                invalidUserException.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user));
        assertEquals("Password is null.",
                invalidUserException.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(STRING_5_LETTERS);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        assertEquals("Password is too short, use at least 6 characters.",
                invalidUserException.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        int negativeAge = -1;
        user.setAge(negativeAge);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        assertEquals(ILLEGAL_AGE + negativeAge + ", age can't be negative!",
                invalidUserException.getMessage());
    }

    @Test
    void register_ageOverMax_notOk() {
        int ageOverMax = 121;
        user.setAge(ageOverMax);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        assertEquals(ILLEGAL_AGE + ageOverMax + ", age should be lower 120",
                invalidUserException.getMessage());
    }

    @Test
    void register_ageUnderMin_notOk() {
        int ageUnderMin = 0;
        user.setAge(ageUnderMin);
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        assertEquals(ILLEGAL_AGE + ageUnderMin + ", age should be at least 18",
                invalidUserException.getMessage());
    }
}
