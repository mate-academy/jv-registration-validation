package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "Bob";
    private static final String CORRECT_PASSWORD = "qwerty";
    private static final Integer CORRECT_AGE = 18;

    private static final String EMPTY_LOGIN = "";
    private static final String LARGE_LOGIN = "Bartholomew";
    private static final String LOGIN_WITH_SPACES1 = " ";
    private static final String LOGIN_WITH_SPACES2 = "Bob Alicon";
    private static final String LOGIN_WITH_SPACES3 = "B o b";
    private static final String SHORT_PASSWORD = "qwert";
    private static final String LONG_PASSWORD = "qwertyuiopasdfghj";
    private static final Integer TOO_YOUNG_AGE = 17;
    private static final Integer TOO_OLD_AGE = 131;
    private static final Integer NEGATIVE_AGE = -18;

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUsersLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithEmptyLogin_NotOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithLargeLogin_NotOk() {
        user.setLogin(LARGE_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_usersLoginWithSpaces_NotOk() {
        user.setLogin(LOGIN_WITH_SPACES1);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin(LOGIN_WITH_SPACES2);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin(LOGIN_WITH_SPACES3);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_addByExistingLogin_NotOk() {
        Storage.people.add(user);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUsersPassword_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithShortPassword_NotOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userWithLongPassword_NotOk() {
        user.setPassword(LONG_PASSWORD);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUsersAge_NotOk() {
        user.setAge(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooYoungUserAge_NotOk() {
        user.setAge(TOO_YOUNG_AGE);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_tooOldUserAge_NotOk() {
        user.setAge(TOO_OLD_AGE);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeUsersAge_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_addUserWithCorrectData() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(1, actual.getId());
    }

    @Test
    void register_checkStorageSize() {
        user.setLogin("Alice");
        registrationService.register(user);
        User secondUser = new User();
        secondUser.setLogin("Mike");
        secondUser.setPassword(CORRECT_PASSWORD);
        secondUser.setAge(CORRECT_AGE);
        registrationService.register(secondUser);
        User thirdUser = new User();
        thirdUser.setLogin("Jake");
        thirdUser.setPassword(CORRECT_PASSWORD);
        thirdUser.setAge(CORRECT_AGE);
        registrationService.register(thirdUser);
        assertEquals(3, Storage.people.size());
    }
}
