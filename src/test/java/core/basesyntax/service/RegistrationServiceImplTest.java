package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.InvalidDataException;
import core.basesyntax.exeptions.UserIsNullException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "correctLogin";
    private static final String INCORRECT_LOGIN = "login";
    private static final String CORRECT_PASSWORD = "qwerty";
    private static final String INCORRECT_PASSWORD = "pass";
    private static final int CORRECT_AGE = 20;
    private static final int INCORRECT_AGE = 15;
    private static final int NEGATIVE_AGE = -5;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setAge(CORRECT_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(CORRECT_LOGIN);
    }

    @Test
    void register_userIsNull_NotOk() {
        user = null;
        assertThrows(UserIsNullException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidAge_NotOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsNegative_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidPassword_NotOk() {
        user.setPassword(INCORRECT_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidLogin_NotOk() {
        user.setLogin(INCORRECT_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserThatIsAlreadyExist_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}