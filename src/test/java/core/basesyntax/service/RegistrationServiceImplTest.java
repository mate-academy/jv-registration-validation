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
    private static final String EMPTY_STRING = "";
    private static final String STRING_5_LETTERS = "short";
    private static final String STRING_3_LETTERS = "0v.";
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
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin(STRING_5_LETTERS);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        user.setLogin(EMPTY_STRING);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        user.setLogin(STRING_3_LETTERS);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(STRING_5_LETTERS);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        user.setPassword(EMPTY_STRING);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        user.setPassword(STRING_3_LETTERS);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
    }

    @Test
    void register_negativeAge_notOk() {
        int negativeAge = -1;
        user.setAge(negativeAge);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        negativeAge = -25;
        user.setAge(negativeAge);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        negativeAge = -15;
        user.setAge(negativeAge);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
    }

    @Test
    void register_ageOverMax_notOk() {
        int ageOverMax = 121;
        user.setAge(ageOverMax);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        ageOverMax = 125;
        user.setAge(ageOverMax);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
        ageOverMax = 150;
        user.setAge(ageOverMax);
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(user), EXCEPTION_MUST_BE_THROWN);
    }

    @Test
    void register_ageUnderMin_notOk() {
        int ageUnderMin = 0;
        user.setAge(ageUnderMin);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                EXCEPTION_MUST_BE_THROWN);
        ageUnderMin = 17;
        user.setAge(ageUnderMin);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                EXCEPTION_MUST_BE_THROWN);
        ageUnderMin = 5;
        user.setAge(ageUnderMin);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user),
                EXCEPTION_MUST_BE_THROWN);
    }
}
